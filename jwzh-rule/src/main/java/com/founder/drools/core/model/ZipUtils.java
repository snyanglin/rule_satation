package com.founder.drools.core.model;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.ZipUtils.java]  
 * @ClassName:    [ZipUtils]   
 * @Description:  [压缩ZIP]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月17日 上午11:37:42]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月17日 上午11:37:42，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class ZipUtils {
	private static byte[] buf = new byte[1024];
	
	public static void main(String[] args) throws Exception {
		ZipUtils.zipFiles("D:/work/drl/export","D:/work/drl/export.zip");
		File file=new File("D:/work/drl/export.zip");
		FileInputStream in = new FileInputStream(file);
		byte[] bytes=new byte[1024*1024];
		in.read(bytes);
		ZipUtils.unZipFile(bytes,"D:/work/drl/import");
	}
	
	/**‘
	 * 
	 * @Title: zipFiles
	 * @Description: TODO(压缩zip)
	 * @param @param src 需要压缩的文件或目录
	 * @param @param target 压缩后的zip存放目录
	 * @return void    返回类型
	 * @throw
	 */
	public static void zipFiles(String src, String target) { 
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(target))); 
			File file = new File(src);
			if(file.isDirectory())
				zipFiles(file, out,file.getName());
			else
				zipFiles(file, out,"");
			out.closeEntry(); 
			out.close();  
		} catch (IOException e) { 
       	 	e.printStackTrace();
       	 	throw new RuntimeException("文件打包失败！");
		}
	}
	
	/**
	 * 
	 * @Title: zipFiles
	 * @Description: TODO(压缩ZIP)
	 * @param @param srcFile
	 * @param @param out
	 * @param @param base
	 * @param @throws IOException    设定文件
	 * @return void    返回类型
	 * @throw
	 */
    private static void zipFiles(File srcFile, ZipOutputStream out,String base) throws IOException {  
        if (srcFile.isDirectory()) {//目录
			File[] inputFiles = srcFile.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < inputFiles.length; i++) {//迭代压缩
				zipFiles(inputFiles[i], out, base + inputFiles[i].getName());
			}
		} else {//文件
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(srcFile.getName()));
			}
			FileInputStream in = new FileInputStream(srcFile);
			try {
				int c;				
				while ((c = in.read(buf)) != -1) {
					out.write(buf, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		} 
    }
    
    /**
     * 
     * @Title: unZipFile
     * @Description: TODO(解压ZIP)
     * @param @param bytes
     * @param @param basePath    设定文件
     * @return void    返回类型
     * @throw
     */
    public static void unZipFile(byte[] bytes,String basePath){
    	ZipInputStream in=new ZipInputStream(new ByteArrayInputStream(bytes));
    	try{
    		ZipEntry zipEntry = in.getNextEntry();
    		while(zipEntry!=null){
    			File file=new File(basePath+"/"+zipEntry.getName());
    			if (zipEntry.isDirectory()) {  
    				file.mkdirs();
    			}else{
    				file.createNewFile();
    				FileOutputStream out = new FileOutputStream(file);  
    				int b = 0;  
    				while ((b = in.read()) != -1){  
    					out.write(b);  
    					out.flush();
    				}  
    				out.close();  

    			}
    			zipEntry = in.getNextEntry();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}