package net.oppakolba.canes.client.renderer.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeamParticle extends TextureSheetParticle {
    protected BeamParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        this.lifetime = 20 + this.random.nextInt(10);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

@OnlyIn(Dist.CLIENT)
public static class Provider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public Provider(SpriteSet pSprites) {
        this.sprites = pSprites;
    }

    public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        BeamParticle particle = new BeamParticle(pLevel, pX, pY, pZ);
        particle.pickSprite(sprites);
        return particle;
    }
}
}
