package kys.guestbook.service;

import kys.guestbook.dto.GuestbookDTO;
import kys.guestbook.dto.PageRequestDTO;
import kys.guestbook.dto.PageResultDTO;
import kys.guestbook.entity.GuestBook;
import kys.guestbook.repository.GuestBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestBookServiceTest {

    @Autowired
    GuestBookService guestBookService;

    @Test
    void register(){
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("dto title")
                .content("dto content")
                .writer("user dto")
                .build();

        Long register = guestBookService.register(dto);
        System.out.println("register = " + register);
    }

    @Test
    void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1).size(10).build();

        PageResultDTO<GuestbookDTO, GuestBook> resultDTO = guestBookService.getList(pageRequestDTO);

        System.out.println("prev = " + resultDTO.isPrev());
        System.out.println("Next = " + resultDTO.isNext());
        System.out.println("TotalPage = " + resultDTO.getTotalPage());

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println("guestbookDTO = " + guestbookDTO);
        }



    }
}