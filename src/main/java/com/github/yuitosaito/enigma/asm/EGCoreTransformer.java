package com.github.yuitosaito.enigma.asm;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;


public class EGCoreTransformer implements IClassTransformer {
    //IClassTransformerにより呼ばれる書き換え用のメソッド。
    @Override
    public byte[] transform(final String name, final String transformedName, byte[] bytes) {
        //対象クラス以外を除外する。対象は呼び出し元があるクラスである。
        if (!"net.minecraft.client.entity.EntityClientPlayerMP".equals(transformedName)) return bytes;
        System.out.println("*****Found Class*****");
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(1);
        ClassVisitor cv = new ClassVisitor(ASM4, cw) {
            //クラス内のメソッドを訪れる。
            @Override
            public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, methodName, desc, signature, exceptions);
                //呼び出し元のメソッドを参照していることを確認する。
                String s1 = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, methodName, desc);
                if (s1.equals("sendChatMessage") || s1.equals("func_71165_d") || methodName.equals("sendChatMessage") || methodName.equals("func_71165_d")) {
                    System.out.println("*****Found Method*****");
                    mv = new MethodVisitor(ASM4, mv) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
                            super.visitVarInsn(ALOAD, 1);
                            super.visitMethodInsn(INVOKESTATIC, "com/github/yuitosaito/enigma/asm/EGCoreHook",
                                    "EGCoreStringHook", "(Ljava/lang/String;)Ljava/lang/String;", false);
                            super.visitVarInsn(ASTORE, 1);
                            super.visitVarInsn(ALOAD, 1);
                            Label label = new Label();
                            super.visitJumpInsn(IFNONNULL, label);
                            super.visitInsn(RETURN);
                            super.visitLabel(label);
                            super.visitFrame(F_SAME, 0, null, 0, null);
                        }
                    };
                }
                return mv;
            }
        };
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }
}