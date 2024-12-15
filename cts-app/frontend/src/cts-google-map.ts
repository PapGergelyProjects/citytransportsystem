import { LitElement, html, css } from "lit";
import { customElement, property, query, state } from 'lit/decorators.js'
import { Loader } from '@googlemaps/js-api-loader'

const pin = 'M215.7 499.2C267 435 384 279.4 384 192C384 86 298 0 192 0S0 86 0 192c0 87.4 117 243 168.3 307.2c12.3 15.3 35.1 15.3 47.4 0zM192 128a64 64 0 1 1 0 128 64 64 0 1 1 0-128z';
const cat2 = 'M320 192h17.1c22.1 38.3 63.5 64 110.9 64c11 0 21.8-1.4 32-4v4 32V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V339.2L280 448h56c17.7 0 32 14.3 32 32s-14.3 32-32 32H192c-53 0-96-43-96-96V192.5c0-16.1-12-29.8-28-31.8l-7.9-1c-17.5-2.2-30-18.2-27.8-35.7s18.2-30 35.7-27.8l7.9 1c48 6 84.1 46.8 84.1 95.3v85.3c34.4-51.7 93.2-85.8 160-85.8zm160 26.5v0c-10 3.5-20.8 5.5-32 5.5c-28.4 0-54-12.4-71.6-32h0c-3.7-4.1-7-8.5-9.9-13.2C357.3 164 352 146.6 352 128v0V32 12 10.7C352 4.8 356.7 .1 362.6 0h.2c3.3 0 6.4 1.6 8.4 4.2l0 .1L384 21.3l27.2 36.3L416 64h64l4.8-6.4L512 21.3 524.8 4.3l0-.1c2-2.6 5.1-4.2 8.4-4.2h.2C539.3 .1 544 4.8 544 10.7V12 32v96c0 17.3-4.6 33.6-12.6 47.6c-11.3 19.8-29.6 35.2-51.4 42.9zM432 128a16 16 0 1 0 -32 0 16 16 0 1 0 32 0zm48 16a16 16 0 1 0 0-32 16 16 0 1 0 0 32z';

export interface Coordinate {
    lat: number;
    lng: number;
    radius?: number;
}

export interface MapMarker{
    title: string;
    colorCode?: string;
    coordinate: Coordinate;
}

export interface ClientToServerEvents{
    clickOnMapEvent(event: Coordinate): void;
    saveMarker(marker: MapMarker): void;
}

export class InitMapData {
    title: string = "";
    coordinate: Coordinate = {lat: 0.0, lng: 0.0};
    zoomMagnitude?: number = 11;
}

@customElement("cts-google-map")
export class CtsGoogleMap extends LitElement{
    
    private $server!: ClientToServerEvents;
    
    @property()
    protected apiKey: string = "";

    @property()
    protected mapId: string = "";

    @property()
    protected initialData!: string;
    
    @property()
    protected previousMarkers: string = "";
    
    @query("#ctsMap")
    protected mapDivElement!: HTMLElement;

    protected markerList: google.maps.marker.AdvancedMarkerElement[] = [];
    protected circleList: google.maps.Circle[] = [];
    protected googleMap!: google.maps.Map;
    protected googleMapLoader!: google.maps.MapsLibrary;
    protected googleMarkerLoader!: google.maps.MarkerLibrary;
    
    constructor(){
        super();
    }
    
    private async initMap(data: InitMapData) {
        let loadGoogleApi: google.maps.MapsLibrary = await new Loader({
            apiKey: this.apiKey,
            version: 'weekly',
            region: 'HU'
        }).importLibrary("maps");
        this.googleMapLoader = loadGoogleApi;
        let map = new loadGoogleApi.Map(this.mapDivElement, {
            center: {lat:data.coordinate.lat, lng:data.coordinate.lng},
            zoom: data.zoomMagnitude,
            mapId: this.mapId
        });
        map.addListener('click', (e: any) => {
            const lat = e.latLng.lat();
            const lng = e.latLng.lng();
            this.removeMarkers();
            this.clickOnMap({lat: lat, lng: lng});
        });
        let markerLib: google.maps.MarkerLibrary = await new Loader({
            apiKey: this.apiKey,
            version: 'weekly',
            region: 'HU'
        }).importLibrary('marker');
        this.googleMarkerLoader = markerLib;
        this.addMarker({title:data.title, coordinate: {lat:data.coordinate.lat, lng:data.coordinate.lng}});
        this.googleMap = map;
    }
    
    protected async setCenter(coord: Coordinate){
        this.googleMap!.setCenter({lat: coord.lat, lng: coord.lng});
        await this.addMarker({title: "", coordinate: coord});
    }
    
    private async clickOnMap(event: Coordinate){
        return this.$server!.clickOnMapEvent(event);
    }
    
    protected async addMarker(mark: MapMarker) {
        let pinElement: google.maps.marker.PinElement = new google.maps.marker.PinElement({
            background: "#ff0000"
        });
        let marker = new this.googleMarkerLoader.AdvancedMarkerElement({
            position: {lat:mark.coordinate.lat, lng:mark.coordinate.lng},
            map: this.googleMap,
            title: mark.title,
            content: pinElement.element
        });
        this.$server!.saveMarker(mark);
        this.markerList.push(marker);
    }
    
    protected async addCustomMarker(mark: MapMarker) {
        let pinElement: google.maps.marker.PinElement = new google.maps.marker.PinElement({
            background: mark.colorCode
        });
        let marker = new this.googleMarkerLoader.AdvancedMarkerElement({
            position: {lat:mark.coordinate.lat, lng:mark.coordinate.lng},
            map: this.googleMap,
            title: mark.title,
            content: pinElement.element,
        });
        this.$server!.saveMarker(mark);
        this.markerList.push(marker);
    }
    
    protected async removeMarkers(){
        this.markerList.forEach((e: google.maps.marker.AdvancedMarkerElement) => {
            e.map = null
        });
        this.markerList = [];
        this.circleList.forEach((e: google.maps.Circle) => e.setMap(null));
        this.circleList = [];        
    }
    
    protected async drawCircle(center: Coordinate){
        let circle = new this.googleMapLoader.Circle({
            map: this.googleMap,
            strokeColor: '#6600cc',
            fillOpacity: 0.1,
            center: {lat: center.lat, lng: center.lng},
            radius: center.radius,
            clickable: false
        });
        this.circleList.push(circle);
    }

    connectedCallback(): void {
        super.connectedCallback();
        let initObj: InitMapData = JSON.parse(this.initialData);
        this.initMap(initObj).then((non) => {
            let markerList: MapMarker[] = JSON.parse(this.previousMarkers);
            markerList.forEach(marker => {
                if(marker.colorCode){
                    this.addCustomMarker(marker);
                } else {
                    this.addMarker(marker);
                    if(marker.coordinate.radius){
                        this.drawCircle(marker.coordinate);
                    }
                }
            });
        });
    }

    disconnectedCallback(): void {
        super.disconnectedCallback();
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
    
    static get is(){
        return 'cts-google-map';
    }
}
