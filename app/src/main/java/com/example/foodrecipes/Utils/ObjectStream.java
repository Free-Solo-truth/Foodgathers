package com.example.foodrecipes.Utils;

import java.io.*;
public class ObjectStream extends ObjectOutputStream{
    public ObjectStream(OutputStream out) throws IOException {
        super(out);
    }

    protected ObjectStream() throws IOException, SecurityException {
    }

    @Override
    protected void writeStreamHeader() throws IOException {}

}


