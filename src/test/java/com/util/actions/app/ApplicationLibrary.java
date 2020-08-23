package com.util.actions.app;

import com.util.actions.common.GenericsLibrary;

public class ApplicationLibrary extends GenericsLibrary {

	private static String params;

	private static String badgeId;

	public void setAndGenerateParams(String page, String pageSize, String fromDate, String toDate, String inname, 
			String min, String max, String id, String site) {
		String param="";
		try {
			String dateFormat = "yyyy-MM-dd";

			if(page == null || page.equalsIgnoreCase("null"))
				page = "";
			if(pageSize == null || pageSize.equalsIgnoreCase("null"))
				pageSize = "";
			if(fromDate == null || fromDate.equalsIgnoreCase("null"))
				fromDate = "";
			if(toDate == null || toDate.equalsIgnoreCase("null"))
				toDate = "";
			if(inname == null || inname.equalsIgnoreCase("null"))
				inname = "";
			if(min == null || min.equalsIgnoreCase("null"))
				min = "";
			if(max == null || max.equalsIgnoreCase("null"))
				max = "";
			if(id == null || id.equalsIgnoreCase("null"))
				id = "";
			else if(id.contains("\""))
				id = id.replaceAll("\"", "");

			if(site == null || site.equalsIgnoreCase("null"))
				site = "";

			setBadgeId(id);

			if(fromDate!=null && !fromDate.isEmpty()) {
				fromDate = getDateInMillis(fromDate, dateFormat);
				if(fromDate!=null && fromDate.length()>9)
					fromDate = fromDate.substring(0, 10);
			}

			if(toDate!=null && !toDate.isEmpty() && toDate.length()>9) {
				toDate = getDateInMillis(toDate, dateFormat);
				if(toDate!=null && toDate.length()>9)
					toDate = toDate.substring(0, 10);
			}
			param = "?page="+page+"&pagesize="+pageSize+"&fromdate="+fromDate+"&todate="+toDate+"&order=desc&sort=rank&inname="+inname+"&min="+min+"&max="+max+"&site="+site;
			System.out.println("param string generated::"+param);
		} catch (Exception e) {
			param = "";
			e.printStackTrace();
		}
		setParams(param);
	}

	public void setParams(String params) {
		ApplicationLibrary.params = params;
	}
	public String getParams() {
		return params;
	}

	public void updateParams(String type) {
		if(type!=null && (type.equalsIgnoreCase("GetBadgesByRecipientsAPI") || type.equalsIgnoreCase("GetBadgesByRecipientsIdAPI")) ) {
			String temp = getParams();
			temp = temp.replaceAll("&order=.*&sort=.*&inname=.*&min=.*&max=.*&site", "&site");
			setParams(temp);
		}
	}

	public void setBadgeId(String badgeId) {
		ApplicationLibrary.badgeId = badgeId;
	}
	public String getBadgeId() {
		return badgeId;
	}
}
