precision mediump float;

uniform sampler2D u_TextureUnit;

varying vec2 v_TextureCoordinates;

uniform vec3 u_ChangeColor;

void midifyColor(vec4 color){
    color.r=max(min(color.r, 1.0), 0.0);
    color.g=max(min(color.g, 1.0), 0.0);
    color.b=max(min(color.b, 1.0), 0.0);
    color.a=max(min(color.a, 1.0), 0.0);
}

void main(){
    vec4 nColor = texture2D(u_TextureUnit, v_TextureCoordinates);
    float c= nColor.r*u_ChangeColor.r+nColor.g*u_ChangeColor.g+nColor.b*u_ChangeColor.b;
    gl_FragColor = vec4(c, c, c, nColor.a);
}