package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface WhatsAppService {

    public Map<String,Object> SendToWhatsApp(Map<String,Object> datas) throws Exception ;

}
