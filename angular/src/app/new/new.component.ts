import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'app-new',
	templateUrl: './new.component.html',
	styleUrls: ['./new.component.scss']
})
export class NewComponent implements OnInit {

	constructor() { }

	ngOnInit(): void {
	}
	
	title = 'app';
	bar_name = 'Schedule new wire transfer';
}
