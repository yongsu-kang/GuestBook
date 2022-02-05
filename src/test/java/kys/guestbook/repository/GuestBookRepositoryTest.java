package kys.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kys.guestbook.entity.GuestBook;
import kys.guestbook.entity.QGuestBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestBookRepositoryTest {

    @Autowired GuestBookRepository guestBookRepository;

    @Test
    void insertDummyData(){
        IntStream.rangeClosed(1,300).forEach(i->{
            GuestBook guestBook = GuestBook.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            guestBookRepository.save(guestBook);
        });
    }


    @Test
    void findAll(){
        List<GuestBook> all = guestBookRepository.findAll();
        for (GuestBook guestBook : all) {
            System.out.println("guestBook = " + guestBook.getGno());
        }
    }
    @Test
    void findById(){
        Optional<GuestBook> guestBook = guestBookRepository.findById(1L);
        System.out.println("guestBook = " + guestBook.get().getTitle());
    }

    @Test
    void update(){
        Optional<GuestBook> findGuest = guestBookRepository.findById(1L);
        if (findGuest.isPresent()){
            GuestBook guestBook = findGuest.get();
            guestBook.changeContent("update content");
            guestBook.changeTitle("update title");
            guestBookRepository.save(guestBook);
        }
    }

    @Test
    void singleDSLTest(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook; //1번

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();//2

        BooleanExpression expression = qGuestBook.writer.contains(keyword);//3

        builder.and(expression);//4

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);//5

        for (GuestBook guestBook : result) {
            System.out.println("guestBook = " + guestBook);
        }
    }

    @Test
    void multiDSLTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook; //1번

        String keyword = "5";

        BooleanBuilder builder = new BooleanBuilder();//2

        BooleanExpression expression = qGuestBook.content.contains(keyword)
                .or(qGuestBook.title.contains(keyword));//3

        builder.and(expression).and(qGuestBook.gno.gt(0L));//4

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);//5

        for (GuestBook guestBook : result) {
            System.out.println("guestBook = " + guestBook);
        }

    }



}