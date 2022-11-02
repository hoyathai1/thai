package com.travel.thai.repo;

import com.travel.thai.bbs.domain.Board;
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
            board.setType("thai");
            board.setContents("1");
            board.setContentsTxt("1");
            board.setAuthor("박부박");
            board.setTitle(sLine);
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

    @Test
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
}
