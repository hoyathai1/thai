package com.travel.thai.bbs.controller;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.*;
import com.travel.thai.common.util.LogUtil;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.common.util.StringUtils;
import com.travel.thai.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardInformService boardInformService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private BoardCategoryService boardCategoryService;

    @Autowired
    private BookMarkService bookMarkService;

    @Value("${board.img.temp.dir}")
    private String TEMP_DIR;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user, Model model) {
        log.info("[BoardController.list][GET]" + LogUtil.setUserInfo(request, user));
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));

        return "board/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> listAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        log.info("[BoardController.listAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            Page<BoardDto> list = boardService.searchBoard(search);
            map.put("list", list);
            map.put("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
            map.put("search", search);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/inform", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> informListAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        log.info("[BoardController.informListAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            List<BoardInformDto> list = boardInformService.search(search);

            map.put("list", list);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(HttpServletRequest request, @ModelAttribute("search") Search search, Model model
                        , @AuthenticationPrincipal User user) {
        log.info("[BoardController.view][GET]" + LogUtil.setUserInfo(request, user));
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));

        BoardDto board = boardService.searchOne(search);
        int likes = likesService.getLikesCount(search.getBoardNum());
        boolean isLikes = false;
        boolean isBookMark = false;

        if (user != null) {
            isLikes = likesService.isLikesByUserId(search.getBoardNum(), user.getUserId());

            BookMark bookMark = new BookMark();
            bookMark.setUser_id(user.getUserId());
            bookMark.setBoard_id(search.getBoardNum());
            isBookMark = bookMarkService.isBookMark(bookMark);
            model.addAttribute("isBookMark", isBookMark);
        } else {
            isLikes = likesService.isLikesByIp(search.getBoardNum(), LogUtil.etRemoteAddr(request));
        }
        boardService.increseViewCount(search);

        model.addAttribute("isLikes", isLikes);
        model.addAttribute("likes", likes);
        model.addAttribute("board", board);

        return "board/view";
    }

    @RequestMapping(value = "/inform", method = RequestMethod.GET)
    public String viewInform(HttpServletRequest request, @ModelAttribute("search") Search search, Model model
            , @AuthenticationPrincipal User user) {
        log.info("[BoardController.viewInform][GET]" + LogUtil.setUserInfo(request, user));

        BoardInformDto boardInform = boardInformService.searchOne(search);

        model.addAttribute("board", boardInform);

        return "board/inform";
    }

    @RequestMapping(value = "/save/comment", method = RequestMethod.POST)
    public ResponseEntity<String> saveComment(HttpServletRequest request, @RequestBody CommentDto commentDto, @AuthenticationPrincipal User user)  {
        log.info("[BoardController.saveComment][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<String> entity = null;

        try {
            commentDto.setIp(LogUtil.etRemoteAddr(request));
            Comment result = commentService.saveComment(commentDto, user);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/search/comment", method = RequestMethod.POST)
    public ResponseEntity<String> searchComment(HttpServletRequest request, @RequestBody Search search, @AuthenticationPrincipal User user) {
        log.info("[BoardController.searchComment][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<String> entity = null;

        try {
            Map<String, Object> map = new HashMap<>();
            CommentDto result = commentService.search(search);

            map.put("list", result.getList());
            map.put("totalCount", result.getCommentTotalCount());
            map.put("pageDto", new PageDto(result.getList().getTotalElements(), result.getList().getPageable()));
            map.put("search", search);

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/delete/comment", method = RequestMethod.POST)
    public ResponseEntity<String> deleteComment(HttpServletRequest request, @RequestBody Search search, @AuthenticationPrincipal User user)  {
        log.info("[BoardController.deleteComment][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<String> entity = null;

        try {
            boolean result = commentService.deleteComment(search, user);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user, Model model) {
        log.info("[BoardController.register][GET]" + LogUtil.setUserInfo(request, user));
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));

        HttpSession session = request.getSession();
        String[] fileList = (String[]) session.getAttribute("fileNameList");

        if (fileList!= null && fileList.length > 0) {
            session.removeAttribute("fileNameList");
        }

        return "board/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerAjax(HttpServletRequest request, @RequestBody Board board, @AuthenticationPrincipal User user) {
        log.info("[BoardController.registerAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<String> entity = null;
        HttpSession session = request.getSession();

        try {
            if (user != null) {
                // 로그인 글쓰기
                board.setUserId(user.getUserId());
                board.setUser(true);
                board.setAuthor(user.getName());
            } else {
                // 비로그인 글쓰기
                board.setUser(false);
                board.setIp(LogUtil.etRemoteAddr(request));
            }

            Board result = boardService.saveBoard(board);
            String[] fileNameList = (String[]) session.getAttribute("fileNameList");

            boardService.moveImage(fileNameList, board);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> uploadAjax(HttpServletRequest request, @RequestParam(value = "uploadFile", required = false) MultipartFile[] uploadfile, @AuthenticationPrincipal User user) {
        log.info("[BoardController.uploadAjax][POST]");
        ResponseEntity<String> entity = null;
        boolean result = false;

        try {
            List<String> fileNameList = new ArrayList<>();
            String[] fileName = new String[uploadfile.length];
            int index = 0;
            for (MultipartFile file : uploadfile) {
                String filename = file.getOriginalFilename();
                String directory = TEMP_DIR;
                String filepath = Paths.get(directory, filename).toString();

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filepath)));

                stream.write(file.getBytes());
                stream.close();

                fileNameList.add(filename);
                fileName[index] = filename;
                index ++;
            }

            HttpSession session = request.getSession();
            session.setAttribute("fileNameList", fileName);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modify(HttpServletRequest request, @ModelAttribute("search") Search search, Model model
            , @AuthenticationPrincipal User user) {
        log.info("[BoardController.modify][GET]" + LogUtil.setUserInfo(request, user));
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));

        try {
            HttpSession session = request.getSession();
            String[] fileList = (String[]) session.getAttribute("fileNameList");

            if (fileList!= null && fileList.length > 0) {
                session.removeAttribute("fileNameList");
            }

            if (user != null) {
                BoardDto board = boardService.searchOne(search);

                if (user.getUserId().equals(board.getUserId())) {
                    board.setTypeName(boardCategoryService.getBoardTypeName(board.getType(),board.getCategory()));
                    model.addAttribute("board", board);
                    model.addAttribute("search", search);
                } else {
                    return "redirect:/warning";
                }
            } else {
                if (StringUtils.isEmpty(search.getPassword())) {
                    return "redirect:/warning";
                }
                String encPassword = RSAUtil.decryptRSA(search.getPassword());
                search.setPassword(encPassword);
                boolean result = boardService.isCheckPassword(search);

                if (!result) {
                    return "redirect:/warning";
                }

                BoardDto board = boardService.searchOne(search);
                board.setTypeName(boardCategoryService.getBoardTypeName(board.getType(),board.getCategory()));
                model.addAttribute("board", board);
                model.addAttribute("search", search);
            }
        } catch (Exception e) {
            return "redirect:/warning";
        }

        return "board/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modifyAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Board board, @AuthenticationPrincipal User user) {
        log.info("[BoardController.modifyAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<String> entity = null;
        HttpSession session = request.getSession();

        try {
            boolean result = boardService.modifyBoard(board, user);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");
            boardService.moveImage(fileNameList, board);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }

    /**
     * 게시물 비밀번호 확인
     *
     * @param request
     * @param response
     * @param search
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> checkAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Search search, @AuthenticationPrincipal User user) {
        log.info("[BoardController.checkAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity = null;

        try {
            Map<String, Object> map = new HashMap<>();

            if ("modify".equals(search.getType())) {
                boolean result = boardService.isCheckPassword(search);

                if (result) {
                    // 비밀번호 일치
                    String encPassword = RSAUtil.encryptRSA(search.getPassword());
                    String url = "/board/modify?boardNum=" + search.getBoardNum() + "&password=" + encPassword;
                    map.put("redirect", url);
                }

                map.put("result", result);
            } else if ("delete".equals(search.getType())) {
                boolean result = boardService.isCheckPassword(search);
                BoardDto baordDto = boardService.searchOne(search);
                if (result && baordDto.isUser()) {
                    boardService.deleteBoard(search);
                }

                String url = "/board/list";
                map.put("redirect", url);
                map.put("result", result);
            }

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/deleteByOwner", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> deleteByOwnerAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Search search, @AuthenticationPrincipal User user) {
        log.info("[BoardController.deleteByOwnerAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity = null;
        boolean result = false;
        String url = "";

        try {
            Map<String, Object> map = new HashMap<>();

            BoardDto board = boardService.searchOne(search);

            if (user != null && board != null) {
                if (user.getUserId().equals(board.getUserId())) {
                    boardService.deleteBoard(search);

                    result = true;
                    url = "/board/list?type=all&best=&category=thai&pageNum=0";
                }
            }

            map.put("redirect", url);
            map.put("result", result);

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modifyByOwner", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> modifyByOwnerAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Search search, @AuthenticationPrincipal User user) {
        log.info("[BoardController.modifyByOwner][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity = null;
        boolean result = false;
        String url = "";

        try {
            Map<String, Object> map = new HashMap<>();

            BoardDto board = boardService.searchOne(search);

            if (user != null && board != null) {
                if (user.getUserId().equals(board.getUserId())) {
                    result = true;
                    url = "/board/modify?boardNum=" + search.getBoardNum();
                }
            }

            map.put("redirect", url);
            map.put("result", result);

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/likes", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> likesAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody LikesDto likesDto, @AuthenticationPrincipal User user) {
        log.info("[BoardController.likesAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity = null;
        boolean result = false;

        try {
            Map<String, Object> map = new HashMap<>();

            if (user != null) {
                likesDto.setUserId(user.getUserId());
                result = likesService.likeThePost(likesDto);
            } else {
                likesDto.setIp(LogUtil.etRemoteAddr(request));
                result = likesService.likeThePost(likesDto);
            }

            int likesCount = likesService.getLikesCount(likesDto.getBoardId());
            map.put("likes", likesCount);
            map.put("isLikes", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/bookmark", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> bookmarkAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody BookMark bookMark, @AuthenticationPrincipal User user) {
        log.info("[BoardController.bookmarkAjax][POST]" + LogUtil.setUserInfo(request, user));
        ResponseEntity<Map<String, Object>> entity = null;
        boolean result = true;

        try {
            Map<String, Object> map = new HashMap<>();

            if (user == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            bookMark.setUser_id(user.getUserId());

            if (bookMarkService.isBookMark(bookMark)) {
                // 북마크 이미 되있는경우 삭제
                bookMarkService.delete(bookMark);
            } else {
                bookMarkService.save(bookMark);
            }

            map.put("result", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

}
