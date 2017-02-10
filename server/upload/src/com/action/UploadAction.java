package com.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ��ȡAndroid���ϴ���������Ϣ
 * 
 * @author Administrator
 * 
 */

public class UploadAction extends ActionSupport
{
	// �ϴ��ļ���
	private File file;
	// �ϴ��ļ�����
	private String fileContentType;
	// ��װ�ϴ��ļ���
	private String fileName;
	// ��������ע�������
	private String savePath;

	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		FileOutputStream fos = null;
		FileInputStream fis = null;
		
		try
		{
			System.out.println("��ȡAndroid�˴���������ͨ��Ϣ��");
			System.out.println("�û�����" + request.getParameter("username"));
			System.out.println("���룺" + request.getParameter("pwd"));
			System.out.println("���䣺" + request.getParameter("age"));
			System.out.println("�ļ�����" + request.getParameter("fileName"));
			System.out.println("��ȡAndroid�˴��������ļ���Ϣ��");
			System.out.println("�ļ����Ŀ¼: " + getSavePath());
			System.out.println("�ļ�����: " + fileName);
			System.out.println("�ļ���С: " + file.length());
			System.out.println("�ļ�����: " + fileContentType);

			fos = new FileOutputStream(getSavePath() + "/" + getFileName());
			fis = new FileInputStream(getFile());
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1)
			{
				fos.write(buffer, 0, len);
			}
			System.out.println("�ļ��ϴ��ɹ�");
		}
		catch (Exception e)
		{
			System.out.println("�ļ��ϴ�ʧ��");
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally
		{
			close(fos, fis);
		}
		return SUCCESS;
	}

	/**
	 * �ļ����Ŀ¼
	 * 
	 * @return
	 */
	public String getSavePath() throws Exception
	{
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	private void close(FileOutputStream fos, FileInputStream fis)
	{
		if (fis != null)
		{
			try
			{
				fis.close();
				fis = null;
			}
			catch (IOException e)
			{
				System.out.println("FileInputStream�ر�ʧ��");
				e.printStackTrace();
			}
		}
		if (fos != null)
		{
			try
			{
				fos.close();
				fis = null;
			}
			catch (IOException e)
			{
				System.out.println("FileOutputStream�ر�ʧ��");
				e.printStackTrace();
			}
		}
	}

}