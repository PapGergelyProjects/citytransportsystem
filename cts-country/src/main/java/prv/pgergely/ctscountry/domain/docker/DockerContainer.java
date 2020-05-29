package prv.pgergely.ctscountry.domain.docker;

import java.time.LocalDateTime;

import prv.pgergely.ctscountry.utils.docker.ContainerStatus;

public class DockerContainer {
	
    private String containerId;
    private String imageId;
    private LocalDateTime createdAt;
    private String ports;
    private String size;
    private String name;
    private String network;

    public DockerContainer(String containerId, String imageId, LocalDateTime createdAt, String ports, String size, String name, String network) {
        this.containerId = containerId;
        this.imageId = imageId;
        this.createdAt = createdAt;
        this.ports = ports;
        this.size = size;
        this.name = name;
        this.network = network;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getImageId() {
        return imageId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getPorts() {
        return ports;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getNetwork() {
        return network;
    }

    @Override
    public String toString() {
        return "DockerContainer{" + "containerId=" + containerId + ", imageId=" + imageId + ", createdAt=" + createdAt + ", ports=" + ports + ", size=" + size + ", name=" + name + ", network=" + network + '}';
    }
}
