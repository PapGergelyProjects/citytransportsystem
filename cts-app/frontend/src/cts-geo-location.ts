import { LitElement, html, css } from "lit";
import { customElement, property } from 'lit/decorators.js';

export interface Position{
    lat: number;
    lon: number;
    accuracy: number;
    speed: number | null;
}

export interface ClientToServerEvents{
    obtainPosition(event: Position): void;
}

const settings: PositionOptions = {
    enableHighAccuracy: true,
    timeout: 10000,
    maximumAge: 100
}

@customElement("cts-geo-location")
export class CtsGeoLocation extends LitElement{
    
    @property({ type: Object })
    private position?: GeolocationPosition;
    
    @property({ type: Object })
    private error?: GeolocationPositionError;
    
    private $server?: ClientToServerEvents;
    
    private locationId: number = -1;
    
    constructor(){
        super();
    }
    
    render(){
        return html`<div id="ctsGeoLoc">
            ${this.position ? html`
                <p>Lat: ${this.position.coords.latitude}</p>
                <p>Lon: ${this.position.coords.longitude}</p>
                <p>Accuracy: ${this.position.coords.accuracy}</p>
            `: `Updating---`},
            ${this.error ? html`
                <p>Error Message: ${this.error.message}</p>
            ` : `No errors`}
        </div>`;
    }
    
    protected updateLocation(){
        if(this.locationId !== -1){
            navigator.geolocation.clearWatch(this.locationId);
        }
        let positionFunct = (pos: GeolocationPosition) => {
            this.position = pos;
            this.error = undefined;
            let actPos: Position = {
                lat: pos.coords.latitude,
                lon: pos.coords.longitude,
                accuracy: pos.coords.accuracy,
                speed: pos.coords.speed
            };
            this.$server?.obtainPosition(actPos);
        };
        let errorFunct = (err: GeolocationPositionError) => {
            this.error = err;
            this.position = undefined;
        }
        this.locationId = navigator.geolocation.watchPosition(positionFunct, errorFunct, settings);
    }
    
    static get is(){
        return "cts-geo-location";
    }
    
    /*connectedCallback(): void {
        super.connectedCallback();
        const settings: PositionOptions = {
            enableHighAccuracy: true,
            timeout: 10000,
            maximumAge: 100
        }
        
        this.locationId = navigator.geolocation.watchPosition(
        (pos: GeolocationPosition) => {
            this.position = pos;
            this.error = undefined;
            let actPos: Position = {
                lat: pos.coords.latitude,
                lon: pos.coords.longitude,
                accuracy: pos.coords.accuracy,
                speed: pos.coords.speed
            };
            this.$server?.obtainPosition(actPos);
        }, 
        (err: GeolocationPositionError) => {
            this.error = err;
            this.position = undefined;
        }, 
        settings);
    }
    
    disconnectedCallback(): void {
        navigator.geolocation.clearWatch(this.locationId);
        super.disconnectedCallback();
    }*/
}