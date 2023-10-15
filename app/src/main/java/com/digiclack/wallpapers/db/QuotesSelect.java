package com.digiclack.wallpapers.db;

public class QuotesSelect {
    int _collum_id;
    String _color;
    String _end;
    String _font;
    int _id;
    String _shader;
    String _shadow_color;
    String _shadow_dx;
    String _shadow_dy;
    String _shadow_radius;
    String _size;
    String _start;
    int _text_id;
    String _text_italic;
    String _text_strik;
    String _text_underline;
    String _textbold;
    public QuotesSelect(){

    }
    public QuotesSelect(int collum_id, int text_id, String start, String end, String size, String color, String font, String shadow_dx, String dhadow_dy, String shadow_radius, String shadow_color, String shader, String textbold, String text_italic, String text_underline, String text_strik) {
        this._collum_id = collum_id;
        this._text_id = text_id;
        this._start = start;
        this._end = end;
        this._size = size;
        this._color = color;
        this._font = font;
        this._shadow_dx = shadow_dx;
        this._shadow_dy = dhadow_dy;
        this._shadow_radius = shadow_radius;
        this._shadow_color = shadow_color;
        this._shader = shader;
        this._textbold = textbold;
        this._text_italic = text_italic;
        this._text_underline = text_underline;
        this._text_strik = text_strik;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_collum_id() {
        return this._collum_id;
    }

    public void set_collum_id(int _collum_id) {
        this._collum_id = _collum_id;
    }

    public int get_text_id() {
        return this._text_id;
    }

    public void set_text_id(int _text_id) {
        this._text_id = _text_id;
    }

    public String get_start() {
        return this._start;
    }

    public void set_start(String _start) {
        this._start = _start;
    }

    public String get_end() {
        return this._end;
    }

    public void set_end(String _end) {
        this._end = _end;
    }

    public String get_size() {
        return this._size;
    }

    public void set_size(String _size) {
        this._size = _size;
    }

    public String get_color() {
        return this._color;
    }

    public void set_color(String _color) {
        this._color = _color;
    }

    public String get_font() {
        return this._font;
    }

    public void set_font(String _font) {
        this._font = _font;
    }

    public String get_shadow_dx() {
        return this._shadow_dx;
    }

    public void set_shadow_dx(String _shadow_dx) {
        this._shadow_dx = _shadow_dx;
    }

    public String get_shadow_dy() {
        return this._shadow_dy;
    }

    public void set_shadow_dy(String _sdhadow_dy) {
        this._shadow_dy = _sdhadow_dy;
    }

    public String get_shadow_radius() {
        return this._shadow_radius;
    }

    public void set_shadow_radius(String _shadow_radius) {
        this._shadow_radius = _shadow_radius;
    }

    public String get_shadow_color() {
        return this._shadow_color;
    }

    public void set_shadow_color(String _shadow_color) {
        this._shadow_color = _shadow_color;
    }

    public String get_shader() {
        return this._shader;
    }

    public void set_shader(String _shader) {
        this._shader = _shader;
    }

    public String get_textbold() {
        return this._textbold;
    }

    public void set_textbold(String _textbold) {
        this._textbold = _textbold;
    }

    public String get_text_italic() {
        return this._text_italic;
    }

    public void set_text_italic(String _text_italic) {
        this._text_italic = _text_italic;
    }

    public String get_text_underline() {
        return this._text_underline;
    }

    public void set_text_underline(String _text_underline) {
        this._text_underline = _text_underline;
    }

    public String get_text_strik() {
        return this._text_strik;
    }

    public void set_text_strik(String _text_strik) {
        this._text_strik = _text_strik;
    }
}
