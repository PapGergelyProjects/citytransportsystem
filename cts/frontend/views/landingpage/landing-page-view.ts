import { html, LitElement, customElement } from 'lit-element';


import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';

@customElement('landing-page-view')
export class LandingPage extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }
  render() {
    return html`<vaadin-vertical-layout
      style="align-items:center; justify-content: center; text-align: center; height: 100%;"
    >
      <img style="width: 200px;" src="images/empty-plant.png" />
      <h2>This place intentionally left empty</h2>
      <p>It’s a place where you can grow your own UI 🤗</p>
    </vaadin-vertical-layout>`;
  }
}
