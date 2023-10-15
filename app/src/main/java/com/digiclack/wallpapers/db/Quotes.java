package com.digiclack.wallpapers.db;

public class Quotes {
    int _collum_id;
    String _color;
    String _font;
    int _gravity;
    int _id;
    String _logo_image;
    String _scale;
    String _shader;
    String _shadow_color;
    String _shadow_dx;
    String _shadow_dy;
    String _shadow_radius;
    String _size;
    String _template;
    String _text;
    String _text_italic;
    String _text_strik;
    String _text_underline;
    String _textbold;
    String _user_image;
    int blur;
    String filter;
    int filter_percentage;
    String hasAuthor;
    String isCropped;
    int order;
    int pos_x;
    int pos_y;
    int rotation;

    public int getBlur() {
        return this.blur;
    }

    public void setBlur(int blur) {
        this.blur = blur;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    public Quotes(){}

    public Quotes(String template, int collum_id, int gravity, String scale, String text, String size, String color, String font, String shadow_dx, String dhadow_dy, String shadow_radius, String shadow_color, String shader, String textbold, String text_italic, String text_underline, String text_strik, int pos_X, int pos_Y, int rotation, String _user_image, String logo_image, String hasAuthor, int blur, String filter, int filter_percentage, String isCropped, int textOrder) {
        this._template = template;
        this._collum_id = collum_id;
        this._gravity = gravity;
        this._scale = scale;
        this._text = text;
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
        this.pos_x = pos_X;
        this.pos_y = pos_Y;
        this.rotation = rotation;
        this._user_image = _user_image;
        this._logo_image = logo_image;
        this.hasAuthor = hasAuthor;
        this.blur = blur;
        this.filter = filter;
        this.filter_percentage = filter_percentage;
        this.isCropped = isCropped;
        this.order = textOrder;
    }

    public String get_template() {
        return this._template;
    }

    public void set_template(String _template) {
        this._template = _template;
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

    public int get_gravity() {
        return this._gravity;
    }

    public void set_gravity(int _gravity) {
        this._gravity = _gravity;
    }

    public String get_scale() {
        return this._scale;
    }

    public void set_scale(String _scale) {
        this._scale = _scale;
    }

    public String get_text() {
        return this._text;
    }

    public void set_text(String _text) {
        this._text = _text;
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

    public int getPos_x() {
        return this.pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return this.pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String get_logo_image() {
        return this._logo_image;
    }

    public void set_logo_image(String _logo_image) {
        this._logo_image = _logo_image;
    }

    public String get_user_image() {
        return this._user_image;
    }

    public void set_user_image(String _user_image) {
        this._user_image = _user_image;
    }

    public String getHasAuthor() {
        return this.hasAuthor;
    }

    public void setHasAuthor(String hasAuthor) {
        this.hasAuthor = hasAuthor;
    }

    public int getFilter_percentage() {
        return this.filter_percentage;
    }

    public void setFilter_percentage(int filter_percentage) {
        this.filter_percentage = filter_percentage;
    }

    public String getIsCropped() {
        return this.isCropped;
    }

    public void setIsCropped(String isCropped) {
        this.isCropped = isCropped;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
