package prv.pgergely.ctscountry.domain;

public class ResponseData {
	
	public long id;
	public boolean enabled;
	
	@Override
	public String toString() {
		return "ResponseData={id:" + id + ", enabled:" + enabled + "}";
	}
}
