package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.BoardFileRepository;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardFileRepository boardFileRepository;

    @Value("${board.img.dir}")
    private String IMG_DIR;

    @Value("${board.img.temp.dir}")
    private String TEMP_DIR;

    public Page<BoardDto> searchBoard(Search search) {

        if (search.getPageSize() == 0) {
            search.setPageSize(30);
        }

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardRepository.search(search, pageable);
    }

    @Override
    public Page<BoardDto> searchBoardForDetail(Search search) {
        search.setPageSize(10);

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardRepository.searchForDetail(search, pageable);
    }

    @Override
    public BoardDto searchOne(Search search) {
        BoardDto result = boardRepository.searchOne(search);

        List<BoardFileDto> fileDto = boardFileRepository.getImageList(result.getId());

        for (BoardFileDto dto : fileDto) {

            String replaceContent = "";

            if (dto.isDel()) {{
                replaceContent = result.getContents().replace(dto.getName(), "/img/ban.png");
            }} else {
                replaceContent = result.getContents().replace(dto.getName(), dto.getDir()+dto.getName());
            }


            result.setContents(replaceContent);
        }

        return result;
    }

    @Override
    @Transactional
    public Board saveBoard(Board board) {
        try {
            if (!board.isUser()) {
                String encPassword = RSAUtil.encryptRSA(board.getPassword());
                board.setPassword(encPassword);
            }
        } catch (Exception e) {
            // TODO: 예외처리
        }

        Board result = boardRepository.save(board);

        return result;
    }

    @Override
    @Transactional
    public void increseViewCount(Search search) {
        boardRepository.increseViewCount(search);
    }

    @Override
    public boolean isCheckPassword (Search search) {
        boolean isPassword = false;
        try {
            String dbPassword = boardRepository.getPassword(search.getBoardNum());
            //String decPassword = RSAUtil.decryptRSA(search.getPassword());
            String dbDecPassword = RSAUtil.decryptRSA(dbPassword);

            if (dbDecPassword.equals(search.getPassword())) {
                    isPassword = true;
            }
        } catch (Exception e) {
            // TODO: 예외 처리
            // 복호화 에러 있을수있음
            isPassword = false;
        }

        return isPassword;
    }

    @Override
    @Transactional
    public boolean modifyBoard(Board board, User user) {
        boolean isModify = false;

        try {
            if (user != null) {
                Search search = new Search();
                search.setBoardNum(board.getId());
                BoardDto result = boardRepository.searchOne(search);

                if (user.getUserId().equals(result.getUserId())) {
                    boardRepository.modifyBoard(board);
                    isModify = true;
                    return isModify;
                } else {
                    return isModify;
                }
            }

            String dbPassword = boardRepository.getPassword(board.getId());
            String dbDecPassword = RSAUtil.decryptRSA(dbPassword);

            if (dbDecPassword.equals(board.getPassword())) {
                boardRepository.modifyBoard(board);
                isModify = true;
            }
        } catch (Exception e) {
            // TODO: 예외 처리
            // 복호화 에러 있을수있음
            isModify = false;
        }

        return isModify;
    }

    @Override
    @Transactional
    public boolean modifyBoardForAdmin(Board board) {
        boolean isModify = false;

        try {
            boardRepository.modifyBoard(board);
            isModify = true;
        } catch (Exception e) {
            // TODO: 예외 처리
            // 복호화 에러 있을수있음
            isModify = false;
        }
        return isModify;
    }

    @Override
    @Transactional
    public void deleteBoard(Search search) {
        boardRepository.deleteBoard(search.getBoardNum());
    }

    @Override
    @Transactional
    public void restoreBoard(Search search) {
        boardRepository.restoreBoard(search.getBoardNum());
    }

    @Override
    public void moveImage(String[] fileNames, Board board) {
        Long id = board.getId();

        LocalDate today = LocalDate.now();
        String year = String.valueOf(today.getYear());
        String month = String.valueOf(today.getMonthValue());
        String day = String.valueOf(today.getDayOfMonth());
        String dir = year + "/" + month + "/" + day + "/" + id + "/";

        if (fileNames == null || fileNames.length < 1) {
            return;
        } else {
            File fileDir = new File(IMG_DIR + dir);
            if (!fileDir.exists()) {
                try {
                    Path path = Paths.get(IMG_DIR + dir);
                    Files.createDirectories(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (String fileName : fileNames) {
                Path filePath = Paths.get(TEMP_DIR + fileName);
                Path filePathToMove = Paths.get(IMG_DIR + dir + fileName);

                try {
                    Files.move(filePath, filePathToMove);

                    BoardFile boardFile = new BoardFile();
                    boardFile.setBoardId(id);
                    boardFile.setFileName(fileName);
                    boardFile.setDir("/upload/" + dir);
                    boardFileRepository.save(boardFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public BoardDto searchBoardContents(Search search) {

        BoardDto result = boardRepository.searchBoardContent(search);

        List<BoardFileDto> fileDto = boardFileRepository.getImageList(search.getBoardNum());

        for (BoardFileDto dto : fileDto) {
            String replaceContent = "";

            if (dto.isDel()) {{
                replaceContent = result.getContents().replace(dto.getName(), "/img/ban.png");
            }} else {
                replaceContent = result.getContents().replace(dto.getName(), dto.getDir()+dto.getName());
            }
            
            result.setContents(replaceContent);
        }

        return result;
    }

    @Override
    public BoardDto searchBoardDetailForAdmin(Search search) {

        BoardDto result = boardRepository.searchBoardDetailForAdmin(search);

        List<BoardFileDto> fileDto = boardFileRepository.getImageList(search.getBoardNum());

        for (BoardFileDto dto : fileDto) {
            String replaceContent = result.getContents().replace(dto.getName(), dto.getDir()+dto.getName());
            result.setContents(replaceContent);
        }

        return result;
    }

    @Override
    public Page<BoardDto> searchBoardForAdmin(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), 15
        );

        return boardRepository.searchForAdmin(search, pageable);
    }

    @Override
    public Page<BoardDto> searchByIdForPc(Search search) {
        if (search.getPageSize() == 0) {
            search.setPageSize(30);
        }

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardRepository.searchByIdForPc(search, pageable);
    }
}
