package com.xmlparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;


public class Parser
{
	public List getInfo() throws Exception
	{
		List list = new ArrayList();
		
		SAXReader saxReader = new SAXReader();
		
		Document doc = saxReader.read(new File("D:/Program Files/tomcat-6.0.16/webapps/upload/file/1.xml"));
		
		Element root = doc.getRootElement();
		
		Element manufacture = root.element("Manufacture");
		System.out.println(manufacture.getTextTrim());
		
		Element model = root.element("Model");
		System.out.println(model.getTextTrim());
		
		Element hw_version = root.element("HW_version");
		System.out.println(hw_version.getTextTrim());
		
		Element sw_version = root.element("SW_version");
		System.out.println(sw_version.getTextTrim());
		
		Element sn = root.element("SN");
		System.out.println(sn.getTextTrim());
		
		Element runningTime = root.element("RunningTime");
		System.out.println(runningTime.getTextTrim());

		for(Iterator iter = root.elementIterator("app"); iter.hasNext();)  
        {  
            Element e = (Element)iter.next();
            
            System.out.println(e.attributeValue("name"));
            System.out.println(e.elementTextTrim("launchCount"));
    		System.out.println(e.elementTextTrim("usageTime"));
        } 
		
		return list;
	}
	
}