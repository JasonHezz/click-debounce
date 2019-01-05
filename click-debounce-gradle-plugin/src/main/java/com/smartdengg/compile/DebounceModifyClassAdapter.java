package com.smartdengg.compile;

import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.smartdengg.compile.Utils.convertSignature;

/**
 * 创建时间: 2018/03/21 23:00 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public class DebounceModifyClassAdapter extends ClassVisitor implements Opcodes {

  private String className;
  private WeavedClass weavedClass;
  private Map<String, List<MethodDelegate>> unWeavedClassMap;

  public DebounceModifyClassAdapter(ClassVisitor classVisitor,
      Map<String, List<MethodDelegate>> unWeavedClassMap) {
    super(Opcodes.ASM6, classVisitor);
    this.unWeavedClassMap = unWeavedClassMap;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    this.weavedClass = new WeavedClass(className = name);
  }

  @Override
  public MethodVisitor visitMethod(int access, final String name, String desc, String signature,
      String[] exceptions) {

    MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

    // android.view.View.OnClickListener.onClick(android.view.View)
    if (Utils.isViewOnclickMethod(access, name, desc) && isHit(access, name, desc)) {
      methodVisitor = new View$OnClickListenerMethodAdapter(methodVisitor);
      weavedClass.addDebouncedMethod(convertSignature(name, desc));
    }

    // android.widget.AdapterView.OnItemClickListener.onItemClick(android.widget.AdapterView,android.view.View,int,long)
    if (Utils.isListViewOnItemOnclickMethod(access, name, desc) && isHit(access, name, desc)) {
      methodVisitor = new ListView$OnItemClickListenerMethodAdapter(methodVisitor);
      weavedClass.addDebouncedMethod(convertSignature(name, desc));
    }

    return methodVisitor;
  }

  private boolean isHit(int access, String name, String desc) {
    if (unWeavedClassMap == null || unWeavedClassMap.size() == 0) return false;
    boolean hitClass = unWeavedClassMap.containsKey(className);
    if (hitClass) {
      List<MethodDelegate> methodDelegates = unWeavedClassMap.get(weavedClass.className);
      for (int i = 0, n = methodDelegates.size(); i < n; i++) {
        boolean hitMethod = methodDelegates.get(i).match(access, name, desc);
        if (hitMethod) {
          unWeavedClassMap.remove(className);
          return true;
        }
      }
    }
    return false;
  }

  public WeavedClass getWeavedClass() {
    return weavedClass;
  }
}
