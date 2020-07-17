attribute vec4 a_Position;
attribute vec2 a_Coordinate;
uniform mat4 u_Matrix;
uniform mat4 u_CoordinateMatrix;
varying vec2 v_TextureCoordinate;

void main(){
    gl_Position = u_Matrix * a_Position;
    v_TextureCoordinate = (u_CoordinateMatrix * vec4(a_Coordinate,0.1,0.1)).xy;
}