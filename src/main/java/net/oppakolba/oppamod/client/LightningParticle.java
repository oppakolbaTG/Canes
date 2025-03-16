package net.oppakolba.oppamod.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightningParticle extends TextureSheetParticle {
    protected LightningParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet sprites, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.lifetime = 10;
        this.gravity = 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            LightningParticle particle =  new LightningParticle(level, x, y, z, this.sprites, dx, dy, dz);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
