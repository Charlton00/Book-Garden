package com.example.initializer.home;

import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(Long isbn);

    Book findByTitle(String title);

    List<Book> findByAuthors(String authors);

    List<Book> findAllByTitle(String title);

    List<Book> findAllByIsbn(Long isbn);

    @Query(value = "SELECT b.* FROM book b, cart_item c WHERE b.isbn = c.isbn AND c.cart_id = ?1 order by b.title", nativeQuery = true)
	List<Book> findAllCartItems(long userId);

    @Query(value = "SELECT b.* FROM book b WHERE b.title = ?1 OR b.authors = ?1", nativeQuery = true)
    List<Book> findAllBooks(String query);

}
