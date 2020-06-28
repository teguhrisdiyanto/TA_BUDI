package com.ta.apiSB.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class EnkripsiAES256 {
    public String enkripsi (final Map<String, Object> request)
    {
        Map<String, Object> response = new HashMap<String, Object>();

        String jsonString = null;
        Map<String, Object> enkripsiMap = null;
        try {
            if (request.get("enkripsi") instanceof Object) {
                jsonString = (String) request.get("enkripsi").toString();

                String indexAwal = Character.toString(jsonString.charAt(0));
                String indexAkhir = Character.toString(jsonString.charAt(jsonString.length() - 1));

                if (indexAwal.equals("{") && indexAkhir.equals("}")) {

                    enkripsiMap = (Map<String, Object>) request.get("enkripsi");
                    jsonString = new ObjectMapper().writeValueAsString(enkripsiMap);

                }
            }


        } catch (Exception e) {

        }
        return AES.encrypt(jsonString);
    }
}
