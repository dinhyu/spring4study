package pers.spring4.day03.n1_ioc_annotaion.service;

import org.springframework.stereotype.Service;
import pers.spring4.day03.n1_ioc_annotaion.bean.Book;


@Service
public class BookService extends BaseService<Book>{}
