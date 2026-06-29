package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;

@Service
public interface QRCodeService {

    public Map<String,Object> GenerateQRCode(Map<String,Object> response) throws Exception;
   

}
