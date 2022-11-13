package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Page<BoardDto> searchBoard(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardRepository.search(search, pageable);
    }

    @Override
    public BoardDto searchOne(Search search) {
        return boardRepository.searchOne(search);
    }



    @Override
    public Board saveBoard(Board board) {
        try {
            if (!board.isUser()) {
                String encPassword = RSAUtil.encryptRSA(board.getPassword());
                board.setPassword(encPassword);
            }
        } catch (Exception e) {
            // TODO: 예외처리
        }

        return boardRepository.save(board);
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
    public void deleteBoard(Search search) {
        boardRepository.deleteBoard(search.getBoardNum());
    }
}
