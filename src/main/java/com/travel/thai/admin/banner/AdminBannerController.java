package com.travel.thai.admin.banner;

import com.travel.thai.bbs.domain.BoardInform;
import com.travel.thai.bbs.domain.BoardInformDto;
import com.travel.thai.bbs.domain.PageDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.service.BoardCategoryService;
import com.travel.thai.bbs.service.BoardInformService;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import com.travel.thai.common.service.BannerService;
import com.travel.thai.common.service.NoticeService;
import com.travel.thai.common.util.StringUtils;
import com.travel.thai.user.domain.User;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin/banner")
public class AdminBannerController {

    @Autowired
    private BoardCategoryService boardCategoryService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BoardInformService boardInformService;

    @Value("${board.img.banner}")
    private String BANNER;

    @Value("${board.img.temp.banner}")
    private String TEMP;

    @RequestMapping(value = {"/pc"}, method = RequestMethod.GET)
    public String pcList(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        HttpSession session = request.getSession();
        session.removeAttribute("fileName");

        if (StringUtils.isEmpty(search.getCategory())) {
            search.setCategory("thai");
        }
        search.setDevice("PC");
        BannerDto dto = bannerService.getBannerForCategory(search);

        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());
        model.addAttribute("dto", dto);

        return "/admin/banner/pc/list";
    }

    @RequestMapping(value = {"/mobile"}, method = RequestMethod.GET)
    public String mobileList(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        HttpSession session = request.getSession();
        session.removeAttribute("fileName");

        if (StringUtils.isEmpty(search.getCategory())) {
            search.setCategory("thai");
        }
        search.setDevice("MOBILE");
        BannerDto dto = bannerService.getBannerForCategory(search);

        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());
        model.addAttribute("dto", dto);

        return "/admin/banner/mobile/list";
    }

    @RequestMapping(value = {"/board"}, method = RequestMethod.GET)
    public String boardList(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        HttpSession session = request.getSession();
        String[] fileList = (String[]) session.getAttribute("fileNameList");

        if (fileList!= null && fileList.length > 0) {
            session.removeAttribute("fileNameList");
        }

        Page<BoardInformDto> list = boardInformService.searchBannerForAdmin(search);

        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/admin/banner/board/list";
    }

    @RequestMapping(value = {"/pc/view"}, method = RequestMethod.GET)
    public String pcView(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        search.setDevice("PC");
        BannerDto dto = bannerService.getBannerForCategory(search);

        model.addAttribute("dto", dto);

        return "/admin/banner/pc/view";
    }

    @RequestMapping(value = {"/mobile/view1"}, method = RequestMethod.GET)
    public String mobileView1(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        search.setDevice("MOBILE");
        BannerDto dto = bannerService.getBannerForCategory(search);

        model.addAttribute("dto", dto);

        return "/admin/banner/mobile/view1";
    }

    @RequestMapping(value = {"/mobile/view2"}, method = RequestMethod.GET)
    public String mobileView2(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        search.setDevice("MOBILE");
        BannerDto dto = bannerService.getBannerForCategory(search);

        model.addAttribute("dto", dto);

        return "/admin/banner/mobile/view2";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveAjax(HttpServletRequest request, @RequestBody Banner banner
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Boolean> entity;
        HttpSession session = request.getSession();
        boolean result = false;

        try {
            Search search = new Search();
            search.setDevice(banner.getDevice());
            search.setCategory(banner.getCategory());
            search.setPosition(banner.getPosition());
            bannerService.deleteBanner(search);

            String fileName = (String) session.getAttribute("fileName");

            if (StringUtils.isEmpty(fileName)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Path filePath = Paths.get(TEMP + fileName);
            Path filePathToMove = Paths.get(BANNER + fileName);

            Files.move(filePath, filePathToMove, StandardCopyOption.REPLACE_EXISTING);

            banner.setFileName(fileName);
            banner.setDir("/upload/" + BANNER);
            bannerService.saveBanner(banner);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileName");
        return entity;
    }

    @RequestMapping(value = "/saveLink", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveLinkAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Boolean> entity;
        HttpSession session = request.getSession();
        boolean result = false;

        try {
            bannerService.modifyLink(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileName");
        return entity;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> uploadAjax(HttpServletRequest request, @RequestParam(value = "uploadFile", required = false) MultipartFile uploadfile, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity = null;
        boolean result = false;

        try {
            String fileName = "";

            String filename = uploadfile.getOriginalFilename();
            String directory = TEMP;
            String filepath = Paths.get(directory, filename).toString();

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath), true));

            stream.write(uploadfile.getBytes());
            stream.close();

            fileName = filename;

            HttpSession session = request.getSession();
            session.setAttribute("fileName", fileName);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ResponseEntity<Boolean> showAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Boolean> entity;
        HttpSession session = request.getSession();
        boolean result = false;

        try {
            bannerService.showBanner(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileName");
        return entity;
    }

    @RequestMapping(value = "/hidden", method = RequestMethod.POST)
    public ResponseEntity<Boolean> hiddenAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Boolean> entity;
        HttpSession session = request.getSession();
        boolean result = false;

        try {
            bannerService.hiddenBanner(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileName");
        return entity;
    }

    @RequestMapping(value = "/pc/init", method = RequestMethod.POST)
    public ResponseEntity<String> pcInitAjax(HttpServletRequest request, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;

        try {
            bannerService.initCacheForPc();

            entity = new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/mobile/init", method = RequestMethod.POST)
    public ResponseEntity<String> mobileInitAjax(HttpServletRequest request, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;

        try {
            bannerService.initCacheForMobile();

            entity = new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/board/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modifyAjax(HttpServletRequest request, @RequestBody BoardInform boardInform
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        HttpSession session = request.getSession();

        try {
            boardInformService.modifyBoardInform(boardInform);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");
            boardInformService.moveImage(fileNameList, boardInform);

            entity = new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }

    @RequestMapping(value = "/board/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerAjax(HttpServletRequest request, @RequestBody BoardInform boardInform
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        HttpSession session = request.getSession();

        try {
            boardInform.setUserId(user.getUserId());
            boardInform.setBanner(true);
            boardInform.setType("BANNER");
            BoardInform result = boardInformService.save(boardInform);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");

            boardInformService.moveImage(fileNameList, result);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }
}
