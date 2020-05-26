package prv.pgergely.ctscountry.domain.docker;

import java.time.LocalDateTime;

import prv.pgergely.ctscountry.utils.docker.ContainerStatus;

public class DockerContainer {
	
    private String containerId;
    private String imageId;
    private LocalDateTime createdAt;
    private String ports;
    private ContainerStatus status;
    private String size;
    private String name;
    private String network;

    public DockerContainer(String containerId, String imageId, LocalDateTime createdAt, String ports, ContainerStatus status, String size, String name, String network) {
        this.containerId = containerId;
        this.imageId = imageId;
        this.createdAt = createdAt;
        this.ports = ports;
        this.status = status;
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

    public ContainerStatus getStatus() {
        return status;
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
        return "DockerContainer{" + "containerId=" + containerId + ", imageId=" + imageId + ", createdAt=" + createdAt + ", ports=" + ports + ", status=" + status + ", size=" + size + ", name=" + name + ", network=" + network + '}';
    }
}
