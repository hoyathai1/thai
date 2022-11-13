package com.travel.thai.common.service;

import com.travel.thai.user.domain.EmailDTO;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.repository.UserRepository;
import com.travel.thai.user.service.UserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmailService {
    @Autowired
    private UserRepository userRepository;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "hoya.thai.no.1@gmail.com";

    private final UserDetailService userDetailService;

    @Transactional
    public void createTempPwdAndSendEmail(String userEmail, String userId){
        User user = userRepository.searchOne(userId, userEmail);

        if (user == null) {
            // TODO: 예외처리 추가
        }

        String str = getTempPassword();
        EmailDTO dto = new EmailDTO();
        dto.setAddress(userEmail);
        dto.setTitle(user.getName() + "님의 헬로우타이 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. 헬로우타이 임시비밀번호 안내 관련 이메일 입니다." + "[" + user.getName() + "]" +"님의 임시 비밀번호는 "
                + str + " 입니다.");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode(str);
        userRepository.modifyPassword(userId, encPassword);

        mailSend(dto);
    }

    private String getTempPassword(){
        char[] charSet = new char[] { '0', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 4; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    private void mailSend(EmailDTO mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
