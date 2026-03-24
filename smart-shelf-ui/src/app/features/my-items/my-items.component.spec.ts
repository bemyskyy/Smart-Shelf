import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyItemsComponent } from './my-items.component';

describe('MyItemsComponent', () => {
  let component: MyItemsComponent;
  let fixture: ComponentFixture<MyItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyItemsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyItemsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
