package kys.guestbook.service;

import kys.guestbook.dto.GuestbookDTO;
import kys.guestbook.dto.PageRequestDTO;
import kys.guestbook.dto.PageResultDTO;
import kys.guestbook.entity.GuestBook;

public interface GuestBookService {
    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO,GuestBook> getList(PageRequestDTO requestDTO);

    GuestbookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDTO dto);

    default GuestBook dtoToEntity(GuestbookDTO dto){
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getTitle())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(GuestBook entity) {
        GuestbookDTO dto = GuestbookDTO.builder()
                .writer(entity.getWriter())
                .content(entity.getContent())
                .title(entity.getTitle())
                .gno(entity.getGno())
                .modDate(entity.getModDate())
                .regDate(entity.getRegDate())
                .build();

        return dto;
    }
}
