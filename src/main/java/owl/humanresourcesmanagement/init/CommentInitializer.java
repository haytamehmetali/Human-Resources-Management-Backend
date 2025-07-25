package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.Comment;
import owl.humanresourcesmanagement.enums.company.ECommentStatus;

import java.util.*;

public class CommentInitializer {
    public static List<Comment> commentInitializer() {

        List<Comment> comments = new ArrayList<>();

        comments.add(Comment.builder()
                .companyId(1L)
                .userId(1L)
                .position("MANAGER")
                .content("Şirket ortamı oldukça profesyonel ve destekleyici.")
                .status(ECommentStatus.APPROVED)
                .build());

        comments.add(Comment.builder()
                .companyId(2L)
                .userId(2L)
                .position("EMPLOYEE")
                .content("İyi bir deneyimdi, fakat kariyer gelişimi sınırlı.")
                .status(ECommentStatus.APPROVED)
                .build());

        comments.add(Comment.builder()
                .companyId(3L)
                .userId(3L)
                .position("SOFTWARE DEVELOPER")
                .content("Yoğun iş temposu var ama ekip çalışması güçlü.")
                .status(ECommentStatus.APPROVED)
                .build());

        comments.add(Comment.builder()
                .companyId(4L)
                .userId(4L)
                .position("DB ADMIN")
                .content("Yönetim desteği eksikti, iyileştirme yapılmalı.")
                .status(ECommentStatus.APPROVED)
                .build());

        comments.add(Comment.builder()
                .companyId(5L)
                .userId(5L)
                .position("MANAGER")
                .content("Çalışan haklarına önem veriliyor, öneririm.")
                .status(ECommentStatus.PENDING) // Yayınlanmamış yorum → PENDING
                .build());

        return comments;
    }
}
