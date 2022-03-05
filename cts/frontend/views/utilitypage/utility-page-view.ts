import { html, LitElement, customElement } from 'lit-element';


import '@vaadin/vaadin-button';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-text-field';

@customElement('utility-page-view')
export class UtilityPageView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<vaadin-horizontal-layout theme="margin spacing" class="items-end"
      ><vaadin-text-field id="name" label="Your name"></vaadin-text-field>
      <vaadin-button id="sayHello">Say hello</vaadin-button></vaadin-horizontal-layout
    >`;
  }
}
