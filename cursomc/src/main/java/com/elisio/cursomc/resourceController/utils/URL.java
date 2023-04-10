package com.elisio.cursomc.resourceController.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

    public static String decodeParam(String s) {

        //Remove a sujeira dos caracteres especiais e espaços que vem na URI tipo %20(espaço)
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Long> decodeintList(String s) {
        String[] vet = s.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < vet.length; i++) {
            list.add(Long.parseLong(vet[i]));
        }
        return list;
        //Ou mais simples usando lambda
        // return  Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
    }


}
