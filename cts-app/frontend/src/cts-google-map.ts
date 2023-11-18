import { LitElement } from "lit";
import { customElement, property, html, css } from 'lit-element';
import {Loader, LoaderOptions} from 'google-maps';

@customElement("cts-google-map")
export class CtsGoogleMap extends LitElement{
    
    static get styles(){
        return css`
            .cts-map{
                height: 500px;
                width: 500px;
            }
        `;
    }
    
    private opts: LoaderOptions={
        version: 'weekly',
        region: 'HU'
    };
    
    @property()
    protected apiKey: string = "";
    
    protected googleMap: any = undefined;
    
    constructor(){
        super();
    }
    
    protected async initMap() {
        let loader = new Loader(this.apiKey, this.opts);
        let google = await loader.load();
        let googleMapDiv: Element | null | undefined = this.shadowRoot?.querySelector("#ctsMap");
        let map = new google.maps.Map(googleMapDiv!, {
            center:{lat:47.497912, lng:19.040235},
            zoom: 8
        });
        this.googleMap = map;
    }
    
    render(){
        return html`
            <div id="ctsMap" class="cts-map"></div>
        `;
    }
}