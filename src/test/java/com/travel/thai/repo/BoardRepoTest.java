package com.travel.thai.repo;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.domain.BoardType;
import com.travel.thai.bbs.repository.BoardCategoryRepository;
import com.travel.thai.bbs.repository.BoardCategoryTypeRepository;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.common.util.RSAUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class BoardRepoTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCategoryRepository categoryRepository;

    @Autowired
    private BoardCategoryTypeRepository categoryTypeRepository;

//    @Test
    public void save() throws Exception {
        File file = resourceLoader.getResource("classpath:dumyDate.txt").getFile();

        BufferedReader inFile = new BufferedReader(new FileReader(file));
        String sLine = null;

        for (int i=0; i < 999; i++) {
            Board board = new Board();
            String ip1 = String.valueOf((int)(Math.random()*255));
            String ip2 = String.valueOf((int)(Math.random()*255));
            String ip3 = String.valueOf((int)(Math.random()*255));
            String ip4 = String.valueOf((int)(Math.random()*255));
            String textNum = String.valueOf((int)(Math.random()*1000));

            board.setIp(ip1 + "." + ip2 + "." + ip3 + "." + ip4);
            board.setCategory("thai");
            board.setType("board");
            board.setAuthor("임시" + i);
            board.setPassword("1234");
            board.setTitle("[title] " + textNum);
            board.setContents("[content] " + textNum);
            board.setUser(false);

            boardRepository.save(board);
        }

        List<Board> list = boardRepository.findAll();

        list.forEach(
                x-> System.out.println(x.getTitle() + " | " + x.getAuthor() + " | " + x.getContents())
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
                x-> System.out.println("" + x.getTitle() + " | " + x.getAuthor() + " | " + x.getContents())
        );
    }

//    @Test
    public void rsaTest() throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, UnsupportedEncodingException {
        KeyPair keyPair = RSAUtil.genRSAKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 공개키를 Base64 인코딩한 문자일을 만듦
        byte[] bytePublicKey = publicKey.getEncoded();
        String base64PublicKey = Base64.getUrlEncoder().encodeToString(bytePublicKey);

        // 개인키를 Base64 인코딩한 문자열을 만듦
        byte[] bytePrivateKey = privateKey.getEncoded();
        String base64PrivateKey = Base64.getUrlEncoder().encodeToString(bytePrivateKey);

        System.out.println("=========");
        System.out.println("base64PublicKey is " + base64PublicKey);
        System.out.println("=========");
        System.out.println("base64PrivateKey is " + base64PrivateKey);
        System.out.println("=========");

        // base64 암호화한 String 에서 Public Key 를 다시생성한후 암호화 테스트를 진행
//        PublicKey rePublicKey = RSAUtil.getPublicKeyFromBase64Encrypted(base64PublicKey);
        String encryptedRe = RSAUtil.encryptRSA("plainText");
        String decryptedRe = RSAUtil.decryptRSA(encryptedRe);

        // base64 암호화한 String 에서 Private Key 를 다시생성한후 복호화 테스트를 진행
//        PrivateKey privateKeyRe = RSAUtil.getPrivateKeyFromBase64Encrypted(base64PrivateKey);
//        String decryptedReRe = RSAUtil.decryptRSA(encryptedRe, privateKeyRe);


    }


//    @Test
    public void category() {
        BoardCategory bc = new BoardCategory();

        bc.setId("thai");
        bc.setName("태국");
        bc.setUse(true);

        categoryRepository.save(bc);

    }

//    @Test
    public void sub_category() {
        BoardType bt = new BoardType();
        bt.setCategoryId("thai");
        bt.setName("여행");
        bt.setType("board");
        bt.setOrderBy(1);
        bt.setUse(true);
        categoryTypeRepository.save(bt);

        bt = new BoardType();
        bt.setCategoryId("thai");
        bt.setName("뉴스");
        bt.setType("news");
        bt.setOrderBy(2);
        bt.setUse(true);
        categoryTypeRepository.save(bt);

        bt = new BoardType();
        bt.setCategoryId("thai");
        bt.setName("정보");
        bt.setType("info");
        bt.setOrderBy(1);
        bt.setUse(true);
        categoryTypeRepository.save(bt);

        bt = new BoardType();
        bt.setCategoryId("thai");
        bt.setName("이푸알");
        bt.setType("find");
        bt.setOrderBy(1);
        bt.setUse(true);
        categoryTypeRepository.save(bt);


    }
}
