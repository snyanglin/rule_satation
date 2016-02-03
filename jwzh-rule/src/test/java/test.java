
public class test {
	public static void main(String[] args) {
		String par = "[{'jsonParamStr':'{'result':'lgSuccess','zdrylxName':'涉环保重点人员','zdryGllxdm':'30','fsrOrgName':'环保支队','zdryName':'李晓明','fsrUserCode':'666666','fsrName':'市环保支队长','zdryId':'1c1eb27e5d5049858a5651b1614e1b95'}','ruleFileName':'210200_ZDRY_MESSAGE','ruleName':'MESSAGE_ZDRYGL_LGSPJG','resStatus':'1'}]";
		int a1 = par.indexOf(":")+2;
		int a2 = par.indexOf("ruleFileName")-3;
		String parTem = par.substring(a1, a2);
		
		int a3 = par.indexOf("ruleFileName")+15;
		int a4 = par.indexOf("ruleName")-3;
		String fileRuleName = par.substring(a3, a4);

		int a5 = par.indexOf("ruleName")+11;
		int a6 = par.indexOf("resStatus")-3;
		String ruleName= par.substring(a5, a6);
		
		System.out.println(parTem);
		System.out.println(fileRuleName);
		System.out.println(ruleName);
		System.out.println(a1);
	}
}
