package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.BoardFileRepository;
import com.travel.thai.bbs.repository.BoardInformFileRepository;
import com.travel.thai.bbs.repository.BoardInformRepository;
import com.travel.thai.bbs.service.BoardInformService;
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
import java.time.LocalDate;
import java.util.List;

@Service
public class BoardInformServiceImpl implements BoardInformService {

    @Value("${board.img.dir}")
    private String IMG_DIR;

    @Value("${board.img.temp.dir}")
    private String TEMP_DIR;

    @Autowired
    private BoardInformRepository boardInformRepository;

    @Autowired
    private BoardInformFileRepository boardInformFileRepository;

    @Override
    public List<BoardInformDto> search(Search search) {
        return boardInformRepository.search(search);
    }

    @Override
    public Page<BoardInformDto> searchForAdmin(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), 15
        );

        return boardInformRepository.searchForAdmin(search, pageable);
    }


    @Override
    public Page<BoardInformDto> searchBannerForAdmin(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), 15
        );

        return boardInformRepository.searchForBanner(search, pageable);
    }

    @Override
    public BoardInformDto searchOne(Search search) {
        BoardInformDto result = boardInformRepository.searchOne(search);

        List<BoardFileDto> fileDto = boardInformFileRepository.getImageList(result.getId());

        for (BoardFileDto dto : fileDto) {
            String replaceContent = "";
            replaceContent = result.getContents().replace(dto.getName(), dto.getDir()+dto.getName());
            result.setContents(replaceContent);
        }

        return result;
    }

    @Override
    public BoardInformDto searchOneForAdmin(Search search) {

        BoardInformDto result = boardInformRepository.searchOneForAdmin(search);

        List<BoardFileDto> fileDto = boardInformFileRepository.getImageList(result.getId());

        for (BoardFileDto dto : fileDto) {
            String replaceContent = "";
            replaceContent = result.getContents().replace(dto.getName(), dto.getDir()+dto.getName());
            result.setContents(replaceContent);
        }

        return result;
    }

    @Override
    public BoardInformDto searchBannerOneForAdmin(Search search) {
        BoardInformDto result = boardInformRepository.searchBannerOneForAdmin(search);

        List<BoardFileDto> fileDto = boardInformFileRepository.getImageList(result.getId());

        for (BoardFileDto dto : fileDto) {
            String replaceContent = "";
            replaceContent = result.getContents().replace(dto.getName(), dto.getDir()+dto.getName());
            result.setContents(replaceContent);
        }

        return result;
    }

    @Override
    @Transactional
    public void modifyBoardInform(BoardInform boardInform) {
        boardInformRepository.modifyBoardInform(boardInform);
    }

    @Override
    @Transactional
    public void deleteBoardInform(Search search) {
        boardInformRepository.deleteBoardInform(search);
    }

    @Override
    @Transactional
    public void restoreBoardInform(Search search) {
        boardInformRepository.restoreBoardInform(search);
    }

    @Override
    public BoardInform save(BoardInform boardInform) {
        BoardInform result = boardInformRepository.save(boardInform);

        return result;
    }

    @Override
    public void moveImage(String[] fileNames, BoardInform boardInform) {
        Long id = boardInform.getId();

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

                    BoardInformFile boardFile = new BoardInformFile();
                    boardFile.setBoardId(id);
                    boardFile.setFileName(fileName);
                    boardFile.setDir("/upload/" + dir);
                    boardInformFileRepository.save(boardFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
