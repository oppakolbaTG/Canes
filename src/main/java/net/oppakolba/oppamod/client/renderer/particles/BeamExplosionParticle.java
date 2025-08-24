package net.oppakolba.oppamod.client.renderer.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeamExplosionParticle extends TextureSheetParticle {

    public BeamExplosionParticle(ClientLevel level, double x, double y, double z,
                                 double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.scale(3.0F);
        this.lifetime = 10 + this.random.nextInt(10);
        this.hasPhysics = true;


    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp(
                ((float)this.age + scaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F
        );
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            BeamExplosionParticle particle =  new BeamExplosionParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            particle.pickSprite(sprites);
            return particle;
        }
    }
}
