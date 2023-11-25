import { LitElement, html, css } from "lit";
import { customElement, property } from 'lit/decorators.js'
import { Loader, LoaderOptions } from 'google-maps';

export interface Coordinate {
    lat: number;
    lng: number;
    radius?: number;
}

export interface ClientToServerEvents{
    
    clickOnMapEvent(event: Coordinate): string;
}

export class InitMapData {
    title: string = "";
    coordinate: Coordinate = {lat: 0.0, lng: 0.0};
    zoomMagnitude?: number = 11;
}

@customElement("cts-google-map")
export class CtsGoogleMap extends LitElement{
    
    private opts: LoaderOptions={
        version: 'weekly',
        region: 'HU'
    };
    
    private $server?: ClientToServerEvents;
    
    @property()
    protected apiKey: string = "";
    
    protected markerList: Array<google.maps.Marker> = [];
    protected circleList: Array<google.maps.Circle> = [];
    protected googleMap?: google.maps.Map;
    protected googleLoader?: Loader;
    
    constructor(){
        super();
    }
    
    protected async initMap(data: InitMapData) {
        this.googleLoader = new Loader(this.apiKey, this.opts);
        let google = await this.googleLoader!.load();
        let googleMapDiv: Element | null | undefined = this.shadowRoot?.querySelector("#ctsMap");
        let map = new google.maps.Map(googleMapDiv!, {
            center: {lat:data.coordinate.lat, lng:data.coordinate.lng},
            zoom: data.zoomMagnitude
        });
        google.maps.event.addListener(map, "click", (e) => {
            const lat = e.latLng.lat();
            const lng = e.latLng.lng();
            this.removeMarkers();
            this.addMarker("", {lat: lat, lng: lng});
            let res = this.clickOnMap({lat: lat, lng: lng});
            res.then((val) => console.log(val));
        });
        this.addMarker(data.title, {lat:data.coordinate.lat, lng:data.coordinate.lng});
        this.googleMap = map;
    }
    
    private async clickOnMap(event: Coordinate){
        return this.$server!.clickOnMapEvent(event);
    }
    
    protected async addMarker(title: string, coordinate: Coordinate) {
        let google = await this.googleLoader!.load();
        let marker = new google.maps.Marker({
            position: {lat:coordinate.lat, lng:coordinate.lng},
            map: this.googleMap,
            title: title
        });
        this.markerList.push(marker);
    }
    
    protected async removeMarkers(){
        this.markerList.forEach((e: google.maps.Marker) => e.setMap(null));
        this.markerList = [];
    }
    
    protected async darwCircle(center: Coordinate){
        let google = await this.googleLoader!.load();
        let circle = new google.maps.Circle({
            map: this.googleMap,
            strokeColor: '#6600cc',
            fillOpacity: 0.1,
            center: {lat: center.lat, lng: center.lng},
            radius: center.radius
        });
        this.circleList.push(circle);
    }
    
    render(){
        return html`
            <div id="ctsMap" class="cts-map"></div>
        `;
    }
    
    static get styles(){
        return css`
            .cts-map{
                height: 100%;
                width: 100%;
            }
        `;
    }
}