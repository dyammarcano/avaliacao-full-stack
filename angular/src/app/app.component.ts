import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent {

	serverStatus: any = {};

	constructor(private http: HttpClient) {
		this.http.get('api/info/status').subscribe(data => {
			this.serverStatus = data;
			console.log(this.serverStatus);
		});
	}
}
