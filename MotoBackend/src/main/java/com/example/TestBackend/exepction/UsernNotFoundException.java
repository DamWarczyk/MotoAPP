package com.example.TestBackend.exepction;

public class UsernNotFoundException extends RuntimeException{
    public UsernNotFoundException(String massege) {
        super(massege);
    }
}
