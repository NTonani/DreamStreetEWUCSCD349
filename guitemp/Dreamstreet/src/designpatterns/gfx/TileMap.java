package designpatterns.gfx;

import designpatterns.game.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TileMap {

	private ArrayList<Rectangle> tiles;
	private ArrayList<Image> tileImg;
	private int mapSize;
	//Tile images
	public Image TILE_STONE,TILE_GRASS,TILE_WATER;

	public int xOffSet = 0;
	public int yOffSet = 0;


	public TileMap(String path, SpriteSheet map){
		
		TILE_GRASS = new ImageIcon(map.getSprite(107, 33, 32, 32)).getImage();//1
		TILE_STONE = new ImageIcon(map.getSprite(173, 0, 32, 32)).getImage();//2
		TILE_WATER = new ImageIcon(map.getSprite(305,165,32,32)).getImage();//3
		
		tiles = new ArrayList<Rectangle>();
		tileImg = new ArrayList<Image>();
		loadMap(path);	
	}
	
	private void loadMap(String path){
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String curLine;
			
			int row = 0;
			while((curLine = br.readLine())!=null){
				if(curLine.isEmpty())
					continue;
				
				String[] values = curLine.trim().split(" ");
				int col = 0;
				for(String val : values){
					if(!val.isEmpty()){
						int id = Integer.parseInt(val);
						if(id==1){
							tileImg.add(TILE_GRASS);
							tiles.add(new Rectangle(col*32,row*32,32,32));
						}else if(id==2){
							tileImg.add(TILE_STONE);
							tiles.add(new Rectangle(col*32,row*32,32,32));
						}else if(id==3){
							tileImg.add(TILE_WATER);
							tiles.add(new Rectangle(col*32,row*32,32,32));
						}
	
						col++;
					}
				}
				mapSize = col;
				row++;
			}
			mapSize *= row;
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void draw(Graphics g,int scale){
		for(int i =0;i<mapSize;i++) {
			// only draws the tile if its in the bounds of the screen
			if (tiles.get(i).x*scale-xOffSet > 0 - tiles.get(i).height*scale  &&  tiles.get(i).x*scale-xOffSet < Game.WIDTH*scale
			&& tiles.get(i).y*scale-yOffSet > (0 - tiles.get(i).width*scale*1.1) && tiles.get(i).y*scale-yOffSet <  (Game.HEIGHT * scale *1.1))
			{
				g.drawImage(tileImg.get(i),tiles.get(i).x*scale-xOffSet,tiles.get(i).y*scale-yOffSet,tiles.get(i).height*scale,tiles.get(i).width*scale,null);
			}

		}
	}


}
