import { LitElement, html, css } from "lit";
import { customElement, property } from 'lit/decorators.js'
import { Loader, LoaderOptions } from 'google-maps';

export interface Coordinate {
    lat: number,
    lng: number,
    radius?: number
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
    
    @property()
    protected apiKey: string = "";
    
    protected markerList: Array<google.maps.Marker> = [];
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
        this.addMarker(data.title, {lat:data.coordinate.lat, lng:data.coordinate.lng});
        this.googleMap = map;
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