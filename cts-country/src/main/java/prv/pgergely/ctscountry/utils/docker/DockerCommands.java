package prv.pgergely.ctscountry.utils.docker;

public class DockerCommands {
	
	private String imageName;
	private String listImages;
	private String listContainer;
	private String createContainer;
	private String stopContainer;
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getListImages() {
		return listImages;
	}
	public void setListImages(String listImages) {
		this.listImages = listImages;
	}
	public String getListContainer() {
		return listContainer;
	}
	public void setListContainer(String listContainer) {
		this.listContainer = listContainer;
	}
	public String getCreateContainer() {
		return createContainer;
	}
	public void setCreateContainer(String createContainer) {
		this.createContainer = createContainer;
	}
	public String getStopContainer() {
		return stopContainer;
	}
	public void setStopContainer(String stopContainer) {
		this.stopContainer = stopContainer;
	}
}
