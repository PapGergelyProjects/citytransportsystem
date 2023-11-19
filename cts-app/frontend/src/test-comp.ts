import { LitElement, html } from "lit";
import { customElement, property } from 'lit/decorators.js'

@customElement("test-comp")
export class TestComp extends LitElement{
   
   @property()
   protected name: string = "";
   
   private $server?: NotiCompServerInterface;
   
   constructor(){
       super();
   }
   
   private clickOnText(){
       this.$server!.displayNoti("Click test");
   }
   
   public setName(newName: string){
       this.name = newName;
   }
   
    render(){
        return html`
            <div @click=${this.clickOnText}>My comp name is: <b>${this.name}</b></div>
        `;
    }
}

interface NotiCompServerInterface{
    displayNoti(text: string): void;
}