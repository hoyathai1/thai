package com.travel.thai.repo;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

@SpringBootTest
public class BoardRepoTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private BoardRepository boardRepository;

//    @Test
    public void save() throws Exception {
        File file = resourceLoader.getResource("classpath:dumyDate.txt").getFile();

        BufferedReader inFile = new BufferedReader(new FileReader(file));
        String sLine = null;

        while( (sLine = inFile.readLine()) != null ) {
            Board board = new Board();
            board.setContent("1");
            board.setAuthor("박부박");
            board.setTitle(sLine);
            boardRepository.save(board);
        }

        List<Board> list = boardRepository.findAll();

        list.forEach(
                x-> System.out.println(x.getTitle() + " | " + x.getAuthor() + " | " + x.getContent())
        );
    }

//    @Test
    public void delete() throws Exception {
        boardRepository.deleteAll();
    }

//    @Test
    public void findAll () {
        System.out.println("findAll");


        List<Board> list = boardRepository.findAll();

        list.forEach(
                x-> System.out.println("" + x.getTitle() + " | " + x.getAuthor() + " | " + x.getContent())
        );
    }
}
