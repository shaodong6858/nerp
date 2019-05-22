package cn.gs.base.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * soap响应报文解析
 *
 * @author wangshaodong
 * 2019年05月13日
 */
public class SoapUtil {
    
    /**
     * 默认消息工厂
     */
    private static MessageFactory defaultFactory = null;
    
    /**
     * 获取默认消息工厂
     *
     * @return
     * @throws SOAPException
     */
    public static MessageFactory getDefaultMessageFactory() throws SOAPException{
        if(defaultFactory == null){
            defaultFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        }
        return defaultFactory;
    }

    /**
     * 把soap字符串格式化为SOAPMessage
     * 
     * @param soapString
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static SOAPMessage getSoapMessageFromString(String xml) throws SOAPException, IOException {
        SOAPMessage message = getDefaultMessageFactory().createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        return message;
    }
    
    /**
     * 把InputStream格式化为SOAPMessage
     * 
     * @param soapString
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static SOAPMessage getSoapMessageFromInputStream(InputStream in) throws SOAPException, IOException {
        SOAPMessage message = getDefaultMessageFactory().createMessage(new MimeHeaders(), in);
        return message;
    }
    
    private static void PrintBody(Iterator<SOAPElement> iterator, String side) {
        while (iterator.hasNext()) {
            SOAPElement element = iterator.next();
//            System.out.println("Local Name:" + element.getLocalName());
            System.out.print("" + element.getNodeName());
//            System.out.println("Tag Name:" + element.getTagName());
            System.out.println(" : " + element.getValue());
            if (null == element.getValue()
                    && element.getChildElements().hasNext()) {
                PrintBody(element.getChildElements(), side + "-----");
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        System.out.println("开始解析soap...");

        String deptXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://xml.avaya.com/ws/session\" xmlns:ns2=\"http://xml.avaya.com/ws/SystemManagementService/2008/07/01\"><SOAP-ENV:Header><ns1:sessionID/></SOAP-ENV:Header><SOAP-ENV:Body><ns2:releaseResponse><return><result_code>0</result_code><result_data/><message_text/></return></ns2:releaseResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";//<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP:Header/><SOAP:Body><ns:OP_SDMS_Consume_Material_SynResponse xmlns:ns=\"http://toSDMS.material.service.ffcs.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><ns:return><ns:BASEINFO><MSGID>?</MSGID><PMSGID>?</PMSGID><SENDTIME>20140212094609</SENDTIME><S_PROVINCE>?</S_PROVINCE><S_SYSTEM>?</S_SYSTEM><SERVICENAME>?</SERVICENAME><T_PROVINCE>?</T_PROVINCE><T_SYSTEM>?</T_SYSTEM><RETRY>?</RETRY></ns:BASEINFO><ns:MESSAGE><RESULT>E</RESULT><REMARK/><XMLDATA><![CDATA[<response><RESULT>E</RESULT><MESSAGE>平台系统处理时发生异常!保存接口接收数据出错!</MESSAGE></response>]]></XMLDATA></ns:MESSAGE></ns:return></ns:OP_SDMS_Consume_Material_SynResponse></SOAP:Body></SOAP:Envelope>";
        try {
            SOAPMessage msg = getSoapMessageFromString(deptXML);
            SOAPBody body = msg.getSOAPBody();
            Iterator<SOAPElement> iterator = body.getChildElements();
            PrintBody(iterator, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("解析soap结束...");
    }
    
}