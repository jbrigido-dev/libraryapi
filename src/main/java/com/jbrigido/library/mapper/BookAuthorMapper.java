package com.jbrigido.library.mapper;

import com.jbrigido.library.dto.BookAuthorRequestDTO;
import com.jbrigido.library.entity.BookAuthor;
import org.springframework.stereotype.Component;

@Component
public class BookAuthorMapper {

    public BookAuthor toEntity(BookAuthorRequestDTO request ){
        BookAuthor ba = new BookAuthor();
        ba.setAuthor(request.author());
        ba.setBook(request.book());
        return ba;
    }

}
