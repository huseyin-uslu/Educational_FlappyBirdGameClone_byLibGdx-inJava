package com.firstprojects.survival_bird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.physics.box2d.CircleShape;

import java.util.Random;

public class YamyamAyseAtomMustafa extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float enemyVelocity = 10;
	Random random;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;



	ShapeRenderer shapeRenderer;
    //EnemyLocation
	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];
	float distance = 0;
    //Circle
	Circle birdCircle;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;




	@Override
	public void create() {
    batch = new SpriteBatch();
    bird = new Texture("mustafashead.png");
    bee1 = new Texture("ayseshead.png");
    bee2 = new Texture("ayseshead.png");
    bee3 = new Texture("ayseshead.png");
    background = new Texture("background_home.png");
    random = new Random();
    distance = Gdx.graphics.getWidth() / 2 ;

		birdX = Gdx.graphics.getWidth() / 7;
		birdY = Gdx.graphics.getHeight() / 3;

		for(int i = 0; i<numberOfEnemies ; i++) {
			enemyOffSet[i] =  random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13) ;
			enemyOffSet2[i] = random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13);
			enemyOffSet3[i] = random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13);
			enemyX[i] = Gdx.graphics.getWidth() + i * distance;

		}
		//Circles and RenderShapes
		birdCircle    = new Circle();
		enemyCircles  = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];
		//
		shapeRenderer = new ShapeRenderer();

		//Fonts
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().scale(4);

	}


	@Override
	public void render() {
    batch.begin();
   batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
   if(gameState == 1) {//if the game is starting....

	   if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 7){
	   	score++;
		  // System.out.println(" score :" + score);
	   	if(scoredEnemy >= numberOfEnemies - 1) {
	   		scoredEnemy = 0;
		}else {
	   		scoredEnemy++;
		}
	   }

	  // System.out.println(velocity);
   	if(Gdx.input.justTouched()) {
   		velocity = -12;
		}




	   for(int i = 0 ; i<numberOfEnemies ; i++) {

	   	if(enemyX[i] < -Gdx.graphics.getWidth()/20) {
			enemyX[i] = numberOfEnemies * distance;

		}else {
	   		if(score > 5 && score <= 10) {
				enemyX[i] = enemyX[i] - (enemyVelocity + 3) ;
			}else if(score > 10 && score <= 20) {
				enemyX[i] = enemyX[i] - (enemyVelocity + 8);
			}else if(score > 20 && score <= 50) {
				enemyX[i] = enemyX[i] - (enemyVelocity + 15);
			}else if (score > 50){
				enemyX[i] = enemyX[i] - (enemyVelocity + 25);
			}else {
				enemyX[i] = enemyX[i] - enemyVelocity;
			}
		}
		   batch.draw(bee1,enemyX[i],enemyOffSet[i],Gdx.graphics.getWidth() / 20,Gdx.graphics.getHeight() / 10);
		   batch.draw(bee2,enemyX[i],enemyOffSet2[i],Gdx.graphics.getWidth() / 20,Gdx.graphics.getHeight() / 10);
		   batch.draw(bee3,enemyX[i],enemyOffSet3[i],Gdx.graphics.getWidth() / 20,Gdx.graphics.getHeight() / 10);

		   //CircleDraw

		   enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40,enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getHeight() / 30);
		   enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40,enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getHeight() / 30);
		   enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40,enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getHeight() / 30);
	}
   	if(birdY > -Gdx.graphics.getHeight() / 15 && birdY < Gdx.graphics.getHeight() + Gdx.graphics.getHeight() / 15) {
   		velocity = velocity + 0.4f;
   		birdY = birdY - velocity;
	}else {
   		gameState = 2;
   		velocity = 0;



	}

   }else if(gameState == 2) { //if the bird hit with someone...
 //Kus dustugunde ve yandiginda
	   font.draw(batch,"Dokun Tekrar Baslasin.. :)",Gdx.graphics.getWidth() / 5,100);

	   birdY = Gdx.graphics.getHeight() / 3;

	   if (Gdx.input.justTouched()) {
		   gameState = 1 ;
		   scoredEnemy = 0;
		   score = 0;
	   }
	   for(int i = 0; i<numberOfEnemies ; i++) {
		   enemyOffSet[i] =  random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13) ;
		   enemyOffSet2[i] = random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13);
		   enemyOffSet3[i] = random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13);
		   enemyX[i] = Gdx.graphics.getWidth() + i * distance;

		   //Circle Initiliziation
		   enemyCircles[i] = new Circle();
		   enemyCircles2[i] = new Circle();
		   enemyCircles3[i] = new Circle();

	   }

   }else if(gameState == 0) { //by starting game
	   birdY = Gdx.graphics.getHeight() / 3;
   	if (Gdx.input.justTouched()) {
		   gameState = 1 ;

	   }
	   for(int i = 0; i<numberOfEnemies ; i++) {
		   enemyOffSet[i] =  random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13) ;
		   enemyOffSet2[i] = random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13);
		   enemyOffSet3[i] = random.nextInt(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/13);
		   enemyX[i] = Gdx.graphics.getWidth() + i * distance;

		   //Circle Initiliziation
		   enemyCircles[i] = new Circle();
		   enemyCircles2[i] = new Circle();
		   enemyCircles3[i] = new Circle();

	   }
   }






   batch.draw(bird,birdX, birdY ,Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 10);
   font.draw(batch,String.valueOf(score),Gdx.graphics.getWidth() - 250,75);
   batch.end();
    birdCircle.set(birdX + Gdx.graphics.getWidth() / 40, birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getHeight() / 30 );



    for(int i = 0 ; i<numberOfEnemies ; i++){

        if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])){
        	gameState = 2;
		}
	}



	}
	
	@Override
	public void dispose () {

	}
}
