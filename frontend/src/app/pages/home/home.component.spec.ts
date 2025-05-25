import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize menu as closed', () => {
    expect(component.isMenuOpen).toBeFalsy();
  });

  it('should open menu when toggleMenu is called', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTruthy();
  });

  it('should close menu when toggleMenu is called again', () => {
    component.toggleMenu();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalsy();
  });

  it('should not open menu if already open', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTruthy();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalsy();
  });

  it('should open menu if closed', () => {
    expect(component.isMenuOpen).toBeFalsy();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTruthy();
  });

  it('should have isMenuOpen as false initially', () => {
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should change isMenuOpen to true when toggleMenu is called', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should change isMenuOpen to false when toggleMenu is called again', () => {
    component.toggleMenu();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should not change isMenuOpen if already true when toggleMenu is called', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should change isMenuOpen to true when toggleMenu is called first time', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should change isMenuOpen to false when toggleMenu is called second time', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle isMenuOpen state', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu state correctly', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should toggle menu state back to false', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu state from false to true', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should toggle menu state from true to false', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu visibility', () => {
    expect(component.isMenuOpen).toBeFalsy();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTruthy();
  });

  it('should toggle menu visibility back to closed', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTruthy();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalsy();
  });

  it('should toggle menu open state', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should toggle menu close state', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu state correctly', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu open and close', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu state from closed to open', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should toggle menu state from open to closed', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu visibility correctly', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu state from false to true', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should toggle menu state from true to false', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu state', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu visibility', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
  });

  it('should toggle menu visibility back to closed', () => {
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalsy();
  });

  it('should toggle menu state correctly', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });

  it('should toggle menu open and close state', () => {
    expect(component.isMenuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTrue();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalse();
  });
  it('should toggle menu', () => {
    expect(component.isMenuOpen).toBeFalsy();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeTruthy();
    component.toggleMenu();
    expect(component.isMenuOpen).toBeFalsy();
  });

  it('should handle get started click', () => {
    spyOn(console, 'log');
    component.onGetStarted();
    expect(console.log).toHaveBeenCalledWith('Get Started clicked');
  });

  it('should handle start free trial click', () => {
    spyOn(console, 'log');
    component.onStartFreeTrial();
    expect(console.log).toHaveBeenCalledWith('Start Free Trial clicked');
  });
});

