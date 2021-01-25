import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { UploadBusinessesService } from '../upload-businesses.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;files = [];
  constructor(private uploadService: UploadBusinessesService) { }

  ngOnInit(): void { }

  uploadFile(file) {
      const formData = new FormData();
      formData.append('file', file.data);
      file.inProgress = true;
      this.uploadService.upload(formData).pipe(
        map(event => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              file.progress = Math.round(event.loaded * 100 / event.total);
              break;
            case HttpEventType.Response:
              return event;
          }
        }),
        catchError((error: HttpErrorResponse) => {
          return of(error.error);
        })).subscribe((event: any) => {
          if (typeof (event) === 'object') {
            console.log(event.body);
          }
        });


    }

  onClick() {
      const fileUpload = this.fileUpload.nativeElement;fileUpload.onchange = () => {
      for (let index = 0; index < fileUpload.files.length; index++)
      {
       const file = fileUpload.files[index];
       this.files.push({ data: file, inProgress: false, progress: 0});
      }
        this.uploadFiles();
      };
      fileUpload.click();
  }

  private uploadFiles() {
      this.fileUpload.nativeElement.value = '';
      this.files.forEach(file => {
        this.uploadFile(file);
      });
  }
}
