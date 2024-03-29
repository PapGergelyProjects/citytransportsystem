import { LitElement, html, css } from "lit";
import { customElement, property } from 'lit/decorators.js'
import { Loader, LoaderOptions } from 'google-maps';

const pin = 'M215.7 499.2C267 435 384 279.4 384 192C384 86 298 0 192 0S0 86 0 192c0 87.4 117 243 168.3 307.2c12.3 15.3 35.1 15.3 47.4 0zM192 128a64 64 0 1 1 0 128 64 64 0 1 1 0-128z';
const cat2 = 'M320 192h17.1c22.1 38.3 63.5 64 110.9 64c11 0 21.8-1.4 32-4v4 32V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V339.2L280 448h56c17.7 0 32 14.3 32 32s-14.3 32-32 32H192c-53 0-96-43-96-96V192.5c0-16.1-12-29.8-28-31.8l-7.9-1c-17.5-2.2-30-18.2-27.8-35.7s18.2-30 35.7-27.8l7.9 1c48 6 84.1 46.8 84.1 95.3v85.3c34.4-51.7 93.2-85.8 160-85.8zm160 26.5v0c-10 3.5-20.8 5.5-32 5.5c-28.4 0-54-12.4-71.6-32h0c-3.7-4.1-7-8.5-9.9-13.2C357.3 164 352 146.6 352 128v0V32 12 10.7C352 4.8 356.7 .1 362.6 0h.2c3.3 0 6.4 1.6 8.4 4.2l0 .1L384 21.3l27.2 36.3L416 64h64l4.8-6.4L512 21.3 524.8 4.3l0-.1c2-2.6 5.1-4.2 8.4-4.2h.2C539.3 .1 544 4.8 544 10.7V12 32v96c0 17.3-4.6 33.6-12.6 47.6c-11.3 19.8-29.6 35.2-51.4 42.9zM432 128a16 16 0 1 0 -32 0 16 16 0 1 0 32 0zm48 16a16 16 0 1 0 0-32 16 16 0 1 0 0 32z';

export interface Coordinate {
    lat: number;
    lng: number;
    radius?: number;
}

export interface ClientToServerEvents{
    clickOnMapEvent(event: Coordinate): void;
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
            this.clickOnMap({lat: lat, lng: lng});
        });
        this.addMarker(data.title, {lat:data.coordinate.lat, lng:data.coordinate.lng});
        this.googleMap = map;
    }
    
    protected async setCenter(coord: Coordinate){
        this.googleMap!.setCenter({lat: coord.lat, lng: coord.lng});
        await this.addMarker("", coord);
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
    
    protected async addCustomMarker(title: string, colorCode: string, coordinate: Coordinate) {
        let google = await this.googleLoader!.load();
        let marker = new google.maps.Marker({
            position: {lat:coordinate.lat, lng:coordinate.lng},
            map: this.googleMap,
            title: title,
            icon: {
                path: pin,
                fillColor: colorCode,
                fillOpacity: 1,
                scale: 0.075,
            }
        });
        this.markerList.push(marker);
    }
    
    protected async removeMarkers(){
        this.markerList.forEach((e: google.maps.Marker) => e.setMap(null));
        this.markerList = [];
        this.circleList.forEach((e: google.maps.Circle) => e.setMap(null));
        this.circleList = [];        
    }
    
    protected async drawCircle(center: Coordinate){
        let google = await this.googleLoader!.load();
        let circle = new google.maps.Circle({
            map: this.googleMap,
            strokeColor: '#6600cc',
            fillOpacity: 0.1,
            center: {lat: center.lat, lng: center.lng},
            radius: center.radius,
            clickable: false
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